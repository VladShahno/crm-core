package com.crm.verification.core.dto.response.profile;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LeadProfileResponseDto {

  private Set<PackageDataProfileResponseDto> packageData;
  private String verdict;
  private String firstName;
  private String lastName;
  private String email;
  private String title;
  private String proofLink;
  private CompanyProfileResponseDto company;
}
