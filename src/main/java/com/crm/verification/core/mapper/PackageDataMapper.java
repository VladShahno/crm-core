package com.crm.verification.core.mapper;

import com.crm.verification.core.dto.request.packagedata.PackageDataRequestDto;
import com.crm.verification.core.model.PackageData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackageDataMapper {
  PackageData toPackageDataEntity(PackageDataRequestDto packageDataRequestDto);
}
