package com.crm.verification.core.mapper;

import com.crm.verification.core.dto.request.create.CompanyRequestDto;
import com.crm.verification.core.dto.request.update.CompanyUpdatedRequestDto;
import com.crm.verification.core.dto.response.company.CompanyAllResponseDto;
import com.crm.verification.core.dto.response.company.CompanyCreateResponseDto;
import com.crm.verification.core.dto.response.company.CompanyProfileResponseDto;
import com.crm.verification.core.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

  Company toCompanyEntity(CompanyRequestDto companyRequestDto);

  Company toCompanyEntity(CompanyUpdatedRequestDto companyRequestDto);

  CompanyRequestDto toCompanyRequestDto(Company company);

  CompanyProfileResponseDto toCompanyProfileResponseDto(Company company);

  CompanyCreateResponseDto toCompanyCreateResponseDto(Company company);

  CompanyAllResponseDto toCompanyAllResponseDto(Company company);
}
