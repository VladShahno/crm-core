package com.crm.verification.core.dto.response.company;

import java.util.HashSet;
import java.util.Set;

import com.crm.verification.core.dto.response.profile.AddressProfileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyCreateResponseDto {

  private String name;
  private String industry;
  private String employees;
  private String employeesProofLink;
  private String revenue;
  private String revenueProofLink;
  private String companyComments;
  private Set<AddressProfileResponseDto> addresses = new HashSet<>();
}
