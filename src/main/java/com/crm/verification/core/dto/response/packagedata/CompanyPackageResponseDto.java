package com.crm.verification.core.dto.response.packagedata;

import java.util.HashSet;
import java.util.Set;

import com.crm.verification.core.dto.response.profile.AddressProfileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyPackageResponseDto {

  private String name;
  private String industry;
  private Set<AddressProfileResponseDto> addresses = new HashSet<>();
}
