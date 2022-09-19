package com.crm.verification.core.service;

import static com.crm.verification.core.common.Constants.Logging.PACKAGE_ID;
import static com.crm.verification.core.common.Constants.Logging.PACKAGE_NAME;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;
import java.util.stream.Collectors;

import com.crm.verification.core.dto.response.packagedata.PackageDataResponseDto;
import com.crm.verification.core.exception.ResourceExistsException;
import com.crm.verification.core.mapper.PackageDataMapper;
import com.crm.verification.core.model.PackageData;
import com.crm.verification.core.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PackageService {

  private final PackageRepository packageRepository;
  private final PackageDataMapper packageDataMapper;

  public PackageDataResponseDto createPackage(String packageName) {
    if (packageRepository.existsByPackageName(packageName)) {
      log.error("{} already exists", keyValue(PACKAGE_NAME, packageName));
      throw new ResourceExistsException(PACKAGE_NAME + packageName);
    }

    PackageData packageData = new PackageData();
    packageData.setPackageName(packageName);
    var packageDataId = generatePackageId(packageName);
    packageData.setPackageId(packageDataId);

    log.debug("Creating packageData with {}", keyValue(PACKAGE_ID, packageDataId));
    packageRepository.save(packageData);
    return packageDataMapper.toPackageDataResponseDto(packageData);
  }

  public List<PackageDataResponseDto> getAllPackagesByPackageId(String packageId) {
    return packageRepository.findAllByPackageIdOrderByPackageNameAsc(packageId).stream()
        .map(packageDataMapper::toPackageDataResponseDto).collect(Collectors.toList());
  }

  private String generatePackageId(String packageName) {
    var packageDataId = RandomStringUtils.random(10, packageName) + RandomStringUtils.random(10, true, true);
    if (packageRepository.existsByPackageId(packageDataId)) {
      log.warn("{} already exists, generating new id", keyValue(PACKAGE_ID, packageDataId));
      return generatePackageId(packageDataId + packageName);
    }
    log.debug("Generating {}", keyValue(PACKAGE_ID, packageDataId));
    return packageDataId;
  }
}
