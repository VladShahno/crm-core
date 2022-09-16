package com.crm.verification.core.dto.request;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;

import com.crm.verification.core.dto.request.list.PackageDataListRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeadRequestDto {

  private CompanyRequestDto company;
  private Set<PackageDataListRequestDto> packageData;
  private String firstName;
  private String lastName;
  @Email
  private String email;
  private String title;
  private String proofLink;
  private String verdict;
  private String leadComments;
}
