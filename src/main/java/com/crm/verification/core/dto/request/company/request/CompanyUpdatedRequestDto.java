package com.crm.verification.core.dto.request.company.request;

import java.util.HashSet;
import java.util.Set;

import com.crm.verification.core.dto.request.AddressUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdatedRequestDto {

  private String industry;
  private String employees;
  private String employeesProofLink;
  private String revenue;
  private String revenueProofLink;
  private String companyComments;
  private Set<AddressUpdateRequestDto> addresses = new HashSet<>();
}
