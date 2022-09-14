package com.crm.verification.core.mapper;

import com.crm.verification.core.dto.request.CompanyRequestDto;
import com.crm.verification.core.model.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

  Company toCompanyEntity(CompanyRequestDto companyRequestDto);

  CompanyRequestDto toCompanyRequestDto(Company company);
}
