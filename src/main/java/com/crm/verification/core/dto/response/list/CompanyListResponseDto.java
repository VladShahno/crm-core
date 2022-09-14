package com.crm.verification.core.dto.response.list;

import java.util.List;

import lombok.Data;

@Data
public class CompanyListResponseDto {

  private List<AddressListResponseDto> addresses;
  private String name;
  private String industry;
  private String employees;
  private String employeesProofLink;
  private String revenue;
  private String revenueProofLink;
}
