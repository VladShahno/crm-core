package com.crm.verification.core.dto.response.list;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyListResponseDto {

  private List<AddressListResponseDto> addresses;
  private String name;
  private String industry;
  private String employees;
  private String employeesProofLink;
  private String revenue;
  private String revenueProofLink;
}
