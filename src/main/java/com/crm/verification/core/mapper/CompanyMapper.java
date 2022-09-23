package com.crm.verification.core.mapper;

import com.crm.verification.core.dto.request.CompanyRequestDto;
import com.crm.verification.core.dto.request.company.request.CompanyUpdatedRequestDto;
import com.crm.verification.core.dto.request.company.response.CompanyCreateResponseDto;
import com.crm.verification.core.dto.request.company.response.CompanyProfileResponseDto;
import com.crm.verification.core.model.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

  Company toCompanyEntity(CompanyRequestDto companyRequestDto);

  Company toCompanyEntity(CompanyUpdatedRequestDto companyRequestDto);

  CompanyRequestDto toCompanyRequestDto(Company company);

  CompanyProfileResponseDto toCompanyProfileResponseDto(Company company);

  CompanyCreateResponseDto toCompanyCreateResponseDto(Company company);
}
