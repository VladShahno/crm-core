package com.crm.verification.core.dto.response.packagedata;

import com.crm.verification.core.model.Lead;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class LeadPackageResponseDto {

  private CompanyPackageResponseDto company;
  private String firstName;
  private String lastName;
  private String email;
  private String title;
  private String proofLink;
  private String verdict;

  public LeadPackageResponseDto(Lead lead) {
    BeanUtils.copyProperties(lead, this);
  }
}
