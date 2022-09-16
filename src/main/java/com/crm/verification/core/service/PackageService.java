package com.crm.verification.core.service;

import static com.crm.verification.core.common.Constants.Logging.PACKAGE_ID;
import static com.crm.verification.core.common.Constants.Logging.PACKAGE_NAME;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import com.crm.verification.core.dto.request.packagedata.PackageDataRequestDto;
import com.crm.verification.core.dto.response.packagedata.PackageDataResponseDto;
import com.crm.verification.core.exception.ResourceExistsException;
import com.crm.verification.core.mapper.PackageDataMapper;
import com.crm.verification.core.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PackageService {

  private final PackageRepository packageRepository;
  private final PackageDataMapper packageDataMapper;

  public PackageDataResponseDto createPackage(PackageDataRequestDto packageRequestDto) {
    if (packageRepository.existsByPackageName(packageRequestDto.getPackageName())) {
      log.error("{} already exists", keyValue(PACKAGE_NAME, packageRequestDto.getPackageName()));
      throw new ResourceExistsException(PACKAGE_NAME + packageRequestDto.getPackageName());
    }

    var packageData = packageDataMapper.toPackageDataEntity(packageRequestDto);
    var packageDataId = generatePackageId(packageRequestDto.getPackageName());
    packageData.setPackageId(packageDataId);

    log.debug("Creating packageData with {}", keyValue(PACKAGE_ID, packageDataId));
    packageRepository.save(packageData);
    return packageDataMapper.toPackageDataResponseDto(packageData);
  }

  public Page<PackageDataResponseDto> getAllPackagesByPackageId(String packageId, Pageable pageable) {
    return packageRepository.findAllByPackageId(packageId, pageable)
        .map(packageDataMapper::toPackageDataResponseDto);
  }

  private String generatePackageId(String packageName) {
    var packageDataId = RandomStringUtils.random(10, packageName);
    if (packageRepository.existsByPackageId(packageDataId)) {
      log.warn("{} already exists, generating new id", keyValue(PACKAGE_ID, packageDataId));
      return generatePackageId(packageDataId + packageName);
    }
    log.debug("Generating {}", keyValue(PACKAGE_ID, packageDataId));
    return packageDataId;
  }
}
