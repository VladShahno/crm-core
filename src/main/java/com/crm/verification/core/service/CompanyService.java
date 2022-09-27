package com.crm.verification.core.service;

import static com.crm.verification.core.common.Constants.Logging.COMPANY_NOT_FOUND;
import static com.crm.verification.core.common.Constants.Logging.DELETING_COMPANY;
import static com.crm.verification.core.common.Constants.Logging.NAME;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import javax.transaction.Transactional;

import com.crm.verification.core.dto.request.CompanyRequestDto;
import com.crm.verification.core.dto.request.CompanyUpdatedRequestDto;
import com.crm.verification.core.dto.response.company.CompanyAllResponseDto;
import com.crm.verification.core.dto.response.company.CompanyCreateResponseDto;
import com.crm.verification.core.dto.response.company.CompanyProfileResponseDto;
import com.crm.verification.core.exception.ResourceExistsException;
import com.crm.verification.core.exception.ResourceNotFoundException;
import com.crm.verification.core.mapper.CompanyMapper;
import com.crm.verification.core.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Setter
@Service
@AllArgsConstructor
@Transactional
public class CompanyService {

  private final CompanyRepository companyRepository;
  private final CompanyMapper companyMapper;

  public CompanyCreateResponseDto createCompany(CompanyRequestDto companyDto) {
    if (companyRepository.existsByName(companyDto.getName())) {
      log.error("Company with {} already exists", keyValue(NAME, companyDto.getName()));
      throw new ResourceExistsException(NAME + companyDto.getName());
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
          throw new ResourceNotFoundException(NAME + companyName);
        });
    return companyMapper.toCompanyProfileResponseDto(updatedCompany);
  }

  public void deleteCompanyByName(String companyName) {
    companyRepository.findByName(companyName).ifPresentOrElse(company -> {
      log.debug(DELETING_COMPANY, keyValue(NAME, companyName));
      companyRepository.deleteByName(companyName);
    }, () -> {
      log.error(COMPANY_NOT_FOUND, keyValue(NAME, companyName));
      throw new ResourceNotFoundException(NAME + companyName);
    });
  }

  public CompanyAllResponseDto getCompanyByName(String companyName) {
    log.debug("Getting company with {}", keyValue(NAME, companyName));
    return companyRepository.findByName(companyName).map(companyMapper::toCompanyAllResponseDto)
        .orElseThrow(() -> {
          log.error(COMPANY_NOT_FOUND, keyValue(NAME, companyName));
          return new ResourceNotFoundException(NAME + companyName);
        });
  }

  public Page<CompanyAllResponseDto> getAllCompanies(Pageable pageable) {
    log.debug("Getting all companies");
    return companyRepository.findAll(pageable).map(companyMapper::toCompanyAllResponseDto);
  }
}
