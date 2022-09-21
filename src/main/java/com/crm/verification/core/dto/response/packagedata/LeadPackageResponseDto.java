package com.crm.verification.core.dto.response.packagedata;

import java.util.HashSet;
import java.util.Set;

import com.crm.verification.core.model.VerificationResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LeadPackageResponseDto {

  private String firstName;
  private String lastName;
  private String email;
  private String title;
  private String proofLink;
  Set<VerificationResult> verificationResults;
  private CompanyPackageResponseDto company;
}
