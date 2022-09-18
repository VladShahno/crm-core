package com.crm.verification.core.dto.response.packagedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LeadPackageResponseDto {

  private CompanyPackageResponseDto company;
  private String firstName;
  private String lastName;
  private String email;
  private String title;
  private String proofLink;
  private String verdict;
}
