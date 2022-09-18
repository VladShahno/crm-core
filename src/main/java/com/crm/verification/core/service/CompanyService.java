//package com.crm.verification.core.service;
//
//import static com.crm.verification.core.common.Constants.Logging.COMPANY_NOT_FOUND;
//import static com.crm.verification.core.common.Constants.Logging.DELETING_COMPANY;
//import static com.crm.verification.core.common.Constants.Logging.ID;
//import static com.crm.verification.core.common.Constants.Logging.NAME;
//import static net.logstash.logback.argument.StructuredArguments.keyValue;
//
//import com.crm.verification.core.dto.request.CompanyRequestDto;
//import com.crm.verification.core.exception.ResourceExistsException;
//import com.crm.verification.core.exception.ResourceNotFoundException;
//import com.crm.verification.core.mapper.CompanyMapper;
//import com.crm.verification.core.model.Company;
//import com.crm.verification.core.repository.CompanyRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Setter
//@Service
//@RequiredArgsConstructor
//public class CompanyService {
//
//  private final CompanyRepository companyRepository;
//  private CompanyMapper companyMapper;
//
//  public Company createCompany(CompanyRequestDto companyDto) {
//    if (companyRepository.existsByName(companyDto.getName())) {
//      log.error("Company with {} already exists", keyValue(NAME, companyDto.getName()));
//      throw new ResourceExistsException(NAME + companyDto.getName());
//    }
//
//    log.debug("Creating company with {}", keyValue(NAME, companyDto.getName()));
//    return companyRepository.save(companyMapper.toCompanyEntity(companyDto));
//  }
//
//  public Company updateCompanyById(Long id, CompanyRequestDto companyDto) {
//    var company = companyRepository.findById(id)
//        .orElseThrow(() -> new ResourceNotFoundException(ID + id));
//    if (companyRepository.existsByName(companyDto.getName())
//        && !companyDto.getName().equals(company.getName())) {
//      log.error("Can't update company with existing {}", keyValue(NAME, companyDto.getName()));
//      throw new ResourceExistsException(NAME + companyDto.getName());
//    }
//
//    log.debug("Updating company with {}", keyValue(ID, id));
//    var updateCompany = companyMapper.toCompanyEntity(companyDto);
//    updateCompany.setId(id);
//    return companyRepository.save(updateCompany);
//  }
//
//  public void deleteCompanyById(Long id) {
//    companyRepository.findById(id).ifPresentOrElse(company -> {
//      log.debug(DELETING_COMPANY, keyValue(ID, id));
//      companyRepository.deleteById(id);
//    }, () -> {
//      log.error(COMPANY_NOT_FOUND, keyValue(ID, id));
//      throw new ResourceNotFoundException(ID + id);
//    });
//  }
//
//  public void deleteCompanyByName(String name) {
//    companyRepository.findByName(name).ifPresentOrElse(company -> {
//      log.debug(DELETING_COMPANY, keyValue(NAME, name));
//      companyRepository.deleteByName(name);
//    }, () -> {
//      log.error(COMPANY_NOT_FOUND, keyValue(NAME, name));
//      throw new ResourceNotFoundException(NAME + name);
//    });
//  }
//
//  public Company getById(Long id) {
//    log.debug("Getting company with {}", keyValue(ID, id));
//    return companyRepository.findById(id)
//        .orElseThrow(() -> {
//          log.error(COMPANY_NOT_FOUND, keyValue(ID, id));
//          return new ResourceNotFoundException(ID + id);
//        });
//  }
//}
