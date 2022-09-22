package com.crm.verification.core.dto.request;

import java.util.Set;
import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeadRequestDto {

  Set<VerificationResultRequestDto> verificationResults;
  private String firstName;
  private String lastName;
  @Email
  private String email;
  private String title;
  private String proofLink;
  private String leadComments;
  private CompanyRequestDto company;
}
