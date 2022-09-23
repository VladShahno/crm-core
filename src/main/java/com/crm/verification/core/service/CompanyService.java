package com.crm.verification.core.service;

import static com.crm.verification.core.common.Constants.Logging.COMPANY_NOT_FOUND;
import static com.crm.verification.core.common.Constants.Logging.DELETING_COMPANY;
import static com.crm.verification.core.common.Constants.Logging.ID;
import static com.crm.verification.core.common.Constants.Logging.NAME;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import com.crm.verification.core.dto.request.CompanyRequestDto;
import com.crm.verification.core.dto.request.company.request.CompanyUpdatedRequestDto;
import com.crm.verification.core.dto.request.company.response.CompanyCreateResponseDto;
import com.crm.verification.core.dto.request.company.response.CompanyProfileResponseDto;
import com.crm.verification.core.exception.ResourceExistsException;
import com.crm.verification.core.exception.ResourceNotFoundException;
import com.crm.verification.core.mapper.CompanyMapper;
import com.crm.verification.core.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Setter
@Service
@RequiredArgsConstructor
public class CompanyService {

  private final CompanyRepository companyRepository;
  private CompanyMapper companyMapper;

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
    companyRepository.findByName(companyName)
        .orElseThrow(() -> {
          log.error("{} not found", keyValue(NAME, companyName));
          throw new ResourceNotFoundException(NAME + companyName);
        });

    log.debug("Updating company with {}", keyValue(ID, companyName));
    var updatedCompany = companyMapper.toCompanyEntity(companyDto);
    updatedCompany.setName(companyName);
    return companyMapper.toCompanyProfileResponseDto(companyRepository.save(updatedCompany));
  }

  public void deleteCompanyByName(String name) {
    companyRepository.findByName(name).ifPresentOrElse(company -> {
      log.debug(DELETING_COMPANY, keyValue(NAME, name));
      companyRepository.deleteByName(name);
    }, () -> {
      log.error(COMPANY_NOT_FOUND, keyValue(NAME, name));
      throw new ResourceNotFoundException(NAME + name);
    });
  }

  public CompanyProfileResponseDto getCompanyByName(String companyName) {
    log.debug("Getting company with {}", keyValue(NAME, companyName));
    return companyRepository.findByName(companyName).map(companyMapper::toCompanyProfileResponseDto)
        .orElseThrow(() -> {
          log.error(COMPANY_NOT_FOUND, keyValue(NAME, companyName));
          return new ResourceNotFoundException(NAME + companyName);
        });
  }

  public Page<CompanyProfileResponseDto> getAllCompanies(Pageable pageable) {
    log.debug("Getting all companies");
    return companyRepository.findAll(pageable).map(companyMapper::toCompanyProfileResponseDto);
  }
}
