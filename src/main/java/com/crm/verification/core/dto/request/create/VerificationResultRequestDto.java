package com.crm.verification.core.dto.request.create;

import static com.crm.verification.core.common.Constants.CoreServiceValidation.VERIFICATION_RESULT_REQUIRED;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationResultRequestDto {

  @NotBlank(message = VERIFICATION_RESULT_REQUIRED)
  String result;
}
