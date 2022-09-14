package com.crm.verification.core.dto.response.packagedata;

import java.util.List;

import com.crm.verification.core.dto.response.list.AddressListResponseDto;
import lombok.Data;

@Data
public class CompanyPackageResponseDto {

  private List<AddressListResponseDto> addresses;
  private String name;
  private String industry;
  private String employees;
  private String employeesProofLink;
  private String revenue;
  private String revenueProofLink;
}
