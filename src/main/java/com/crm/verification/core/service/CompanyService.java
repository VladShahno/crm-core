package com.crm.verification.core.service;

import static com.crm.verification.core.common.Constants.Logging.ADDRESS;
import static com.crm.verification.core.common.Constants.Logging.COMPANY;
import static com.crm.verification.core.common.Constants.Logging.COMPANY_NOT_FOUND;
import static com.crm.verification.core.common.Constants.Logging.DELETING_COMPANY;
import static com.crm.verification.core.common.Constants.Logging.EMAIL;
import static com.crm.verification.core.common.Constants.Logging.ID;
import static com.crm.verification.core.common.Constants.Logging.LEAD;
import static com.crm.verification.core.common.Constants.Logging.NAME;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.crm.verification.core.dto.request.create.CompanyRequestDto;
import com.crm.verification.core.dto.request.update.CompanyUpdatedRequestDto;
import com.crm.verification.core.dto.response.company.CompanyAllResponseDto;
import com.crm.verification.core.dto.response.company.CompanyCreateResponseDto;
import com.crm.verification.core.dto.response.company.CompanyProfileResponseDto;
import com.crm.verification.core.entity.Company;
import com.crm.verification.core.entity.Lead;
import com.crm.verification.core.exception.ResourceExistsException;
import com.crm.verification.core.exception.ResourceNotFoundException;
import com.crm.verification.core.mapper.CompanyMapper;
import com.crm.verification.core.repository.AddressRepository;
import com.crm.verification.core.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Setter
@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final AddressRepository addressRepository;
  private final LeadService leadService;
  private final CompanyMapper companyMapper;

  public CompanyCreateResponseDto createCompany(CompanyRequestDto companyDto) {
    if (companyRepository.existsByName(companyDto.getName())) {
      log.error("Company with {} already exists", keyValue(NAME, companyDto.getName()));
      throw new ResourceExistsException(COMPANY, NAME, companyDto.getName());
    }

    var company = companyMapper.toCompanyEntity(companyDto);

    log.debug("Creating company with {}", keyValue(NAME, companyDto.getName()));
    return companyMapper.toCompanyCreateResponseDto(companyRepository.save(company));
  }

  public CompanyProfileResponseDto updateCompanyByName(String companyName, CompanyUpdatedRequestDto companyDto) {

    var updatedCompany = companyMapper.toCompanyEntity(companyDto);
    companyRepository.findByName(companyName)
        .ifPresentOrElse(company -> {
          log.debug("Updating company with {}", keyValue(NAME, companyName));
          updatedCompany.setName(companyName);
          updatedCompany.getAddresses().forEach(address -> address.setCompany(updatedCompany));
          companyRepository.save(updatedCompany);
        }, () -> {
          log.error("{} not found", keyValue(NAME, companyName));
          throw new ResourceNotFoundException(COMPANY, NAME, companyName);
        });
    return companyMapper.toCompanyProfileResponseDto(updatedCompany);
  }

  public void deleteCompanyByName(String companyName) {
    companyRepository.findByName(companyName).ifPresentOrElse(company -> {
      log.debug(DELETING_COMPANY, keyValue(NAME, companyName));
      companyRepository.deleteByName(companyName);
    }, () -> {
      log.error(COMPANY_NOT_FOUND, keyValue(NAME, companyName));
      throw new ResourceNotFoundException(COMPANY, NAME, companyName);
    });
  }

  public CompanyAllResponseDto getCompanyByName(String companyName) {
    log.debug("Getting company with {}", keyValue(NAME, companyName));
    return companyRepository.findByName(companyName).map(companyMapper::toCompanyAllResponseDto)
        .orElseThrow(() -> {
          log.error(COMPANY_NOT_FOUND, keyValue(NAME, companyName));
          return new ResourceNotFoundException(COMPANY, NAME, companyName);
        });
  }

  public Page<CompanyAllResponseDto> getAllCompanies(Pageable pageable) {
    log.debug("Getting all companies...");
    return companyRepository.findAll(pageable).map(companyMapper::toCompanyAllResponseDto);
  }

  public Company findCompanyByName(String companyName) {
    return companyRepository.findByName(companyName).orElseThrow(() -> {
      log.error(COMPANY_NOT_FOUND, keyValue(NAME, companyName));
      return new ResourceNotFoundException(COMPANY, NAME, companyName);
    });
  }

  public void removeLeadFromCompany(Lead leadToRemove, Company company) {
    log.debug("Removing lead with {} from company {}", keyValue(EMAIL, leadToRemove.getEmail()),
        keyValue(NAME, company.getName()));
    //maybe need to update old company calling repo
    company.removeLead(leadToRemove);
  }

  public void deleteAddressFromCompany(String companyName, Long addressId) {
    var company = findCompanyByName(companyName);
    company.getAddresses().stream().filter(address -> address.getId().equals(addressId))
        .findFirst().ifPresentOrElse(address -> {
          log.debug("Deleting address with {} from company with {}", keyValue(ID, addressId),
              keyValue(NAME, companyName));
          company.removeAddress(address);
          addressRepository.delete(address);
        }, () -> {
          log.error("Address with {} not found in company with {}", keyValue(ID, addressId),
              keyValue(NAME, companyName));
          throw new ResourceNotFoundException(ADDRESS, ID, addressId.toString());
        });
  }

  private void bulkRemoveLeadFromCompany(Set<String> leadEmails, String companyName) {
    var targetCompany = companyRepository.findByName(companyName);
    var leadsToRemove = leadService.findAllByEmailIn(leadEmails);

    var leadsWithoutCompany = new ArrayList<Lead>();
    if (CollectionUtils.isNotEmpty(leadsToRemove) && targetCompany.isPresent()) {
      leadsToRemove.forEach(lead -> {
        targetCompany.get().removeLead(lead);
        leadsWithoutCompany.add(lead);
      });
      leadService.bulkLeadSave(leadsWithoutCompany);
    }
  }

  @Deprecated(forRemoval = true)
  public CompanyProfileResponseDto addExistingLeadsToCompany(String companyName, Set<String> leadEmails) {
    var targetCompany = findCompanyByName(companyName);
    var addingLeads = leadService.findAllByEmailIn(leadEmails);

    if (CollectionUtils.isNotEmpty(addingLeads)) {
      return addLeadsToCompany(addingLeads, targetCompany, leadEmails);
    }
    log.error("Target leads not found");
    throw new ResourceNotFoundException();
  }

  private CompanyProfileResponseDto addLeadsToCompany(
      List<Lead> addingLeads,
      Company targetCompany,
      Set<String> leadEmails) {

    var leadEmailFromTargetCompany = targetCompany.getLeads()
        .stream()
        .map(Lead::getEmail)
        .collect(Collectors.toSet());

    if (leadEmails.containsAll(leadEmailFromTargetCompany)) {
      log.error("Company already have leads with {}", keyValue(EMAIL, leadEmails));
      throw new ResourceExistsException(LEAD, EMAIL, leadEmails.toString());
    }
    return saveLeadsToCompany(addingLeads, targetCompany);
  }

  private CompanyProfileResponseDto saveLeadsToCompany(List<Lead> addingLeads, Company targetCompany) {
    log.debug("Saving leads {} to company", keyValue(EMAIL, addingLeads.stream().map(Lead::getEmail)));
    addingLeads.forEach(targetCompany::addLeads);
    return companyMapper.toCompanyProfileResponseDto(companyRepository.save(targetCompany));
  }
}
