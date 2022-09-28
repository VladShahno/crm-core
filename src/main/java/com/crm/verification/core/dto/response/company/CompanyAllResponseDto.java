package com.crm.verification.core.dto.response.company;

import java.util.HashSet;
import java.util.Set;

import com.crm.verification.core.dto.response.profile.AddressProfileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAllResponseDto {

  private String name;
  private String industry;
  private String employees;
  private String employeesProofLink;
  private String revenue;
  private String revenueProofLink;
  private String companyComments;
  private Set<CompanyAllLeadResponseDto> leads = new HashSet<>();
  private Set<AddressProfileResponseDto> addresses = new HashSet<>();
}
