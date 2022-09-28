package com.crm.verification.core.dto.request.list;

import static com.crm.verification.core.common.Constants.CoreServiceValidation.PACKAGE_NAME_REQUIRED;

import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.crm.verification.core.dto.request.create.LeadRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PackageDataListRequestDto {

  @NotBlank(message = PACKAGE_NAME_REQUIRED)
  private String packageName;

  @Valid
  private Set<LeadRequestDto> leads = new HashSet<>();
}
