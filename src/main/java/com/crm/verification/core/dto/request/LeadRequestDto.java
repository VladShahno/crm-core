package com.crm.verification.core.dto.request;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.Email;

import com.crm.verification.core.dto.request.list.PackageDataListRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class LeadRequestDto {

  private CompanyRequestDto company;
  @JsonIgnore
  private List<PackageDataListRequestDto> packageData;
  private String firstName;
  private String lastName;
  @Email
  private String email;
  private String title;
  private String proofLink;
  private String verdict;
  private String leadComments;
  private Date created;
}
