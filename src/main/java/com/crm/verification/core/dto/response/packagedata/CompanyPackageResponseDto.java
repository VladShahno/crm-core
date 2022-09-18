package com.crm.verification.core.dto.response.packagedata;

import java.util.HashSet;
import java.util.Set;

import com.crm.verification.core.dto.response.list.AddressListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPackageResponseDto {

  private Set<AddressListResponseDto> addresses = new HashSet<>();
  private String name;
  private String industry;
  private String employees;
  private String employeesProofLink;
  private String revenue;
  private String revenueProofLink;
}
