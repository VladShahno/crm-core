package com.crm.verification.core.mapper;

import com.crm.verification.core.dto.request.LeadRequestDto;
import com.crm.verification.core.model.Lead;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeadMapper {

  Lead toLeadEntity(LeadRequestDto leadRequestDto);

}
