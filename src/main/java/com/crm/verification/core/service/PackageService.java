package com.crm.verification.core.service;

import static com.crm.verification.core.common.Constants.Logging.EMAIL;
import static com.crm.verification.core.common.Constants.Logging.PACKAGE_NAME;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.crm.verification.core.dto.response.packagedata.PackageDataResponseDto;
import com.crm.verification.core.exception.ResourceExistsException;
import com.crm.verification.core.exception.ResourceNotFoundException;
import com.crm.verification.core.mapper.PackageDataMapper;
import com.crm.verification.core.model.Lead;
import com.crm.verification.core.model.PackageData;
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

  public PackageDataResponseDto createPackage(String packageName) {
    if (packageRepository.existsByPackageName(packageName)) {
      log.error("{} already exists", keyValue(PACKAGE_NAME, packageName));
      throw new ResourceExistsException(PACKAGE_NAME + packageName);
    }

    PackageData packageData = new PackageData();
    packageData.setPackageName(packageName);

    return packageDataMapper.toPackageDataResponseDto(packageRepository.save(packageData));
  }

  public PackageDataResponseDto addExistingLeadsToExistingPackage(String packageName, Set<String> leadEmails) {

    var targetPackage = packageRepository.findByPackageName(packageName);
    var addingLeads = leadRepository.findAllByEmailIn(leadEmails);

    if (CollectionUtils.isNotEmpty(addingLeads) && targetPackage.isPresent()) {

      var leadEmailsFromTargetPackage = targetPackage.get().getLeads()
          .stream()
          .map(Lead::getEmail)
          .collect(Collectors.toSet());
      if (leadEmails.containsAll(leadEmailsFromTargetPackage)) {
        log.error("Package already have leads with {}", keyValue(EMAIL, leadEmails));
        throw new ResourceExistsException(EMAIL + leadEmails);
      }

      log.debug("Adding leads {} to {}", keyValue(EMAIL, leadEmails), keyValue(PACKAGE_NAME, packageName));
      addingLeads.forEach(lead -> targetPackage.get().addLeads(lead));
      return packageDataMapper.toPackageDataResponseDto(packageRepository.save(targetPackage.get()));
    }
    log.error("{} or {} not found", keyValue(PACKAGE_NAME, packageName), keyValue(EMAIL, leadEmails));
    throw new ResourceNotFoundException();
  }

  public List<PackageDataResponseDto> getAllPackagesByPackageName(String packageName) {
    return packageRepository.findAllByPackageNameOrderByPackageNameAsc(packageName).stream()
        .map(packageDataMapper::toPackageDataResponseDto).collect(Collectors.toList());
  }
}
