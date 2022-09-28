package com.crm.verification.core.dto.response.packagedata;

import java.util.Set;

import com.crm.verification.core.dto.response.profile.VerificationResultResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadPackageResponseDto {

  Set<VerificationResultResponseDto> verificationResults;
  private String firstName;
  private String lastName;
  private String email;
  private String title;
  private String proofLink;
  private CompanyPackageResponseDto company;
}
