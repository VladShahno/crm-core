package com.crm.verification.core.dto.request;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class CompanyRequestDto {

  @JsonIgnore
  private List<LeadRequestDto> leadList;
  private List<AddressRequestDto> addressList;
  private String name;
  private String industry;
  private String employees;
  private String employeesProofLink;
  private String revenue;
  private String revenueProofLink;
  private String companyComments;
  private Date created;
}
