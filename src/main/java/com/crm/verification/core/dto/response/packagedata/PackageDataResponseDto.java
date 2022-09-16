package com.crm.verification.core.dto.response.packagedata;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackageDataResponseDto {

  private String packageName;
  private String packageId;
  private Set<LeadPackageResponseDto> leads;
}
