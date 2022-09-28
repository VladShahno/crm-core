package com.crm.verification.core.mapper;

import com.crm.verification.core.dto.request.create.LeadRequestDto;
import com.crm.verification.core.dto.request.update.LeadUpdateRequestDto;
import com.crm.verification.core.dto.response.list.LeadListResponseDto;
import com.crm.verification.core.dto.response.profile.LeadProfileResponseDto;
import com.crm.verification.core.entity.Lead;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeadMapper {

  Lead toLeadEntity(LeadRequestDto leadRequestDto);

  @Mapping(target = "verificationResults", ignore = true)
  Lead toLeadEntity(LeadUpdateRequestDto leadRequestDto);

  @Mapping(source = "packageData", target = "packageData")
  LeadListResponseDto toLeadListResponseDto(Lead lead);

  LeadProfileResponseDto toLeadProfileResponseDto(Lead lead);
}
