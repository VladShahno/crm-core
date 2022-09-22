package com.crm.verification.core.dto.response.packagedata;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PackageDataResponseDto {

  private String packageName;
  private Set<LeadPackageResponseDto> leads;
}
