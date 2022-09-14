package com.crm.verification.core.repository.custom;

import com.crm.verification.core.dto.response.list.LeadListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeadCustomRepository {

  Page<LeadListResponseDto> findAllLeads(Pageable pageable);
}
