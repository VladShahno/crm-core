package com.crm.verification.core.dto.response.list;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LeadListResponseDto {

  private Set<PackageDataListResponseDto> packageData;
  private String verdict;
  private String firstName;
  private String lastName;
  private String email;
  private String title;
  private String proofLink;
  private CompanyListResponseDto company;
}
