package com.crm.verification.core.dto.response.packagedata;

import lombok.Data;

@Data
public class LeadPackageResponseDto {

  private CompanyPackageResponseDto company;
  private String firstName;
  private String lastName;
  private String email;
  private String title;
  private String proofLink;
  private String verdict;
}
