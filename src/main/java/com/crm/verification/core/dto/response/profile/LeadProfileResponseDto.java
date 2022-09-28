package com.crm.verification.core.dto.response.profile;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LeadProfileResponseDto {

  Set<VerificationResultResponseDto> verificationResults;
  private Set<PackageDataProfileResponseDto> packageData;
  private String firstName;
  private String lastName;
  private String email;
  private String title;
  private String proofLink;
  private CompanyProfileResponseDto company;
}
