package com.crm.verification.core.dto.request.list;

import java.util.HashSet;
import java.util.Set;

import com.crm.verification.core.dto.request.LeadRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackageDataListRequestDto {
  private String packageName;
  private String packageId;
  private Set<LeadRequestDto> leads = new HashSet<>();
}
