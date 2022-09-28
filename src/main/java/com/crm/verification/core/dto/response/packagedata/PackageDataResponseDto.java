package com.crm.verification.core.dto.response.packagedata;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackageDataResponseDto {

  private String packageName;
  private Set<LeadPackageResponseDto> leads;
}
