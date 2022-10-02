package com.crm.verification.core.service;

import static com.crm.verification.core.common.Constants.Logging.EMAIL;
import static com.crm.verification.core.common.Constants.Logging.LEAD;
import static com.crm.verification.core.common.Constants.Logging.LEAD_NOT_FOUND;
import static com.crm.verification.core.common.Constants.Logging.PACKAGE;
import static com.crm.verification.core.common.Constants.Logging.PACKAGE_NAME;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.crm.verification.core.dto.response.packagedata.PackageDataResponseDto;
import com.crm.verification.core.entity.Lead;
import com.crm.verification.core.entity.PackageData;
import com.crm.verification.core.exception.ResourceExistsException;
import com.crm.verification.core.exception.ResourceNotFoundException;
import com.crm.verification.core.mapper.PackageDataMapper;
import com.crm.verification.core.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PackageService {

  private final PackageRepository packageRepository;
  private final LeadService leadService;
  private final PackageDataMapper packageDataMapper;

  public PackageDataResponseDto createPackage(String packageName, Set<String> leadEmails) {
    if (packageRepository.existsByPackageName(packageName)) {
      log.error("{} already exists", keyValue(PACKAGE_NAME, packageName));
      throw new ResourceExistsException(PACKAGE, PACKAGE_NAME, packageName);
    }

    var addingLeads = leadService.findAllByEmailIn(leadEmails);

    PackageData packageData = new PackageData();
    packageData.setPackageName(packageName);

    if (CollectionUtils.isNotEmpty(addingLeads)) {
      log.debug("Adding leads to new package with {}", keyValue(PACKAGE_NAME, packageName));
      addingLeads.forEach(packageData::addLeads);
    }

    return packageDataMapper.toPackageDataResponseDto(packageRepository.save(packageData));
  }

  public PackageDataResponseDto addExistingLeadsToPackage(String packageName, Set<String> leadEmails) {

    var targetPackage = getPackageByPackageName(packageName);
    var addingLeads = leadService.findAllByEmailIn(leadEmails);
    if (CollectionUtils.isNotEmpty(addingLeads)) {
      log.debug("Adding lead to package with {}", keyValue(PACKAGE_NAME, packageName));
      return addLeadsToPackage(addingLeads, targetPackage, leadEmails);
    }
    log.error("Target package or leads not found");
    throw new ResourceNotFoundException();
  }

  public PackageDataResponseDto removeLeadFromPackage(String leadEmail, String packageName) {
    var targetPackage = getPackageByPackageName(packageName);
    targetPackage.getLeads().stream().filter(lead -> lead.getEmail().equals(leadEmail))
        .findFirst().ifPresentOrElse(lead -> {
          log.debug("Removing lead with {} from package with {}", keyValue(EMAIL, leadEmail),
              keyValue(PACKAGE_NAME, packageName));
          targetPackage.removeLead(lead);
          packageRepository.save(targetPackage);
        }, () -> {
          log.error("Lead with {} not found in package", keyValue(EMAIL, leadEmail));
          throw new ResourceNotFoundException(LEAD, EMAIL, leadEmail);
        });
    return packageDataMapper.toPackageDataResponseDto(targetPackage);
  }

  public PackageDataResponseDto getPackageDataResponseDtoByPackageName(String packageName) {
    return packageRepository.findByPackageName(packageName).map(packageDataMapper::toPackageDataResponseDto)
        .orElseThrow(() -> {
          log.error(LEAD_NOT_FOUND, keyValue(PACKAGE_NAME, packageName));
          return new ResourceNotFoundException(PACKAGE, PACKAGE_NAME, packageName);
        });
  }

  public Page<PackageDataResponseDto> getAllPackages(Pageable pageable) {
    log.debug("Getting all packages...");
    return packageRepository.findAll(pageable).map(packageDataMapper::toPackageDataResponseDto);
  }

  public PackageData getPackageByPackageName(String packageName) {
    return packageRepository.findByPackageName(packageName)
        .orElseThrow(() -> {
          log.error(LEAD_NOT_FOUND, keyValue(PACKAGE_NAME, packageName));
          return new ResourceNotFoundException(PACKAGE, PACKAGE_NAME, packageName);
        });
  }

  private PackageDataResponseDto addLeadsToPackage(
      List<Lead> addingLeads,
      PackageData targetPackage,
      Set<String> leadEmails) {

    var leadEmailsFromTargetPackage = targetPackage.getLeads()
        .stream()
        .map(Lead::getEmail)
        .collect(Collectors.toSet());

    if (!Collections.disjoint(leadEmails, leadEmailsFromTargetPackage)) {
      log.error("Package already have leads with {}", keyValue(EMAIL, leadEmails));
      throw new ResourceExistsException(LEAD, EMAIL, leadEmails.toString());
    }
    return saveLeadsToPackage(addingLeads, targetPackage);
  }

  private PackageDataResponseDto saveLeadsToPackage(List<Lead> addingLeads, PackageData targetPackage) {
    log.debug("Saving leads {} to package", keyValue(EMAIL, addingLeads.stream().map(Lead::getEmail)));
    addingLeads.forEach(targetPackage::addLeads);
    return packageDataMapper.toPackageDataResponseDto(packageRepository.save(targetPackage));
  }
}
