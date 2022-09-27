package com.crm.verification.core.service;

import static com.crm.verification.core.common.Constants.Logging.EMAIL;
import static com.crm.verification.core.common.Constants.Logging.PACKAGE_NAME;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.crm.verification.core.dto.response.packagedata.PackageDataResponseDto;
import com.crm.verification.core.entity.Lead;
import com.crm.verification.core.entity.PackageData;
import com.crm.verification.core.exception.ResourceExistsException;
import com.crm.verification.core.exception.ResourceNotFoundException;
import com.crm.verification.core.mapper.PackageDataMapper;
import com.crm.verification.core.repository.LeadRepository;
import com.crm.verification.core.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PackageService {

  private final PackageRepository packageRepository;
  private final LeadRepository leadRepository;
  private final PackageDataMapper packageDataMapper;

  public PackageDataResponseDto createPackage(String packageName, Set<String> leadEmails) {
    if (packageRepository.existsByPackageName(packageName)) {
      log.error("{} already exists", keyValue(PACKAGE_NAME, packageName));
      throw new ResourceExistsException(PACKAGE_NAME + packageName);
    }

    var existingLeads = leadRepository.findAllByEmailIn(leadEmails);

    PackageData packageData = new PackageData();
    packageData.setPackageName(packageName);

    if (CollectionUtils.isNotEmpty(existingLeads)) {
      existingLeads.forEach(packageData::addLeads);
    }

    return packageDataMapper.toPackageDataResponseDto(packageRepository.save(packageData));
  }

  public PackageDataResponseDto addExistingLeadsToExistingPackage(String packageName, Set<String> leadEmails) {

    var targetPackage = packageRepository.findByPackageName(packageName);
    var addingLeads = leadRepository.findAllByEmailIn(leadEmails);

    return validateWithSavingLeadsToPackage(addingLeads, targetPackage, leadEmails);
  }

  public List<PackageDataResponseDto> getAllPackagesByPackageName(String packageName) {
    return packageRepository.findAllByPackageNameOrderByPackageNameAsc(packageName).stream()
        .map(packageDataMapper::toPackageDataResponseDto).collect(Collectors.toList());
  }

  private PackageDataResponseDto validateWithSavingLeadsToPackage(
      List<Lead> addingLeads,
      Optional<PackageData> targetPackage,
      Set<String> leadEmails) {

    if (CollectionUtils.isNotEmpty(addingLeads) && targetPackage.isPresent()) {
      var leadEmailsFromTargetPackage = targetPackage.get().getLeads()
          .stream()
          .map(Lead::getEmail)
          .collect(Collectors.toSet());
      if (leadEmails.containsAll(leadEmailsFromTargetPackage)) {
        log.error("Package already have leads with {}", keyValue(EMAIL, leadEmails));
        throw new ResourceExistsException(EMAIL + leadEmails);
      }
      return saveLeadsToPackage(addingLeads, targetPackage.get());
    }
    log.error("Target package or leads not found");
    throw new ResourceNotFoundException();
  }

  private PackageDataResponseDto saveLeadsToPackage(List<Lead> addingLeads, PackageData targetPackage) {
    log.debug("Adding leads {} to package", keyValue(EMAIL, addingLeads.stream().map(Lead::getEmail)));
    addingLeads.forEach(targetPackage::addLeads);
    return packageDataMapper.toPackageDataResponseDto(packageRepository.save(targetPackage));
  }
}
