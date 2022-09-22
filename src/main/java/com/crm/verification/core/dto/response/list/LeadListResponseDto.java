package com.crm.verification.core.dto.response.list;

import java.util.Set;

import com.crm.verification.core.dto.response.profile.VerificationResultResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LeadListResponseDto {

  Set<VerificationResultResponseDto> verificationResults;
  private Set<PackageDataListResponseDto> packageData;
  private String firstName;
  private String lastName;
  private String email;
  private String title;
  private String proofLink;
  private CompanyListResponseDto company;
}
