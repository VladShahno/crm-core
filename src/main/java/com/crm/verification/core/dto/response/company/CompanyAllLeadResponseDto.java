package com.crm.verification.core.dto.response.company;

import java.util.Set;

import com.crm.verification.core.dto.response.list.PackageDataListResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAllLeadResponseDto {

  private Set<PackageDataListResponseDto> packageData;
  private String firstName;
  private String lastName;
  private String email;
  private String title;
  private String proofLink;
}
