package com.crm.verification.core.dto.response.profile;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyProfileResponseDto {

  private String name;
  private String industry;
  private String employees;
  private String employeesProofLink;
  private String revenue;
  private String revenueProofLink;
  private Set<AddressProfileResponseDto> addresses = new HashSet<>();
}
