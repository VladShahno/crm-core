package com.crm.verification.core.dto.request;

import java.util.Set;

import com.crm.verification.core.dto.request.company.request.CompanyUpdatedRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeadUpdateRequestDto {

  String verificationResults;
  private String firstName;
  private String lastName;
  private String title;
  private String proofLink;
  private String leadComments;
  private CompanyUpdatedRequestDto company;
}
