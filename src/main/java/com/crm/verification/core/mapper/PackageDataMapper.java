package com.crm.verification.core.mapper;

import com.crm.verification.core.dto.request.list.PackageDataListRequestDto;
import com.crm.verification.core.dto.request.packagedata.PackageDataRequestDto;
import com.crm.verification.core.dto.response.packagedata.PackageDataResponseDto;
import com.crm.verification.core.entity.PackageData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackageDataMapper {
  PackageData toPackageDataEntity(PackageDataRequestDto packageDataRequestDto);

  PackageDataListRequestDto toPackageDataListRequestDto(PackageData packageData);

  PackageDataResponseDto toPackageDataResponseDto(PackageData packageData);
}
