package com.crm.verification.core.dto.request.packagedata;

import static com.crm.verification.core.common.Constants.CoreServiceValidation.PACKAGE_NAME_REQUIRED;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PackageDataRequestDto {

  @NotBlank(message = PACKAGE_NAME_REQUIRED)
  private String packageName;
}
