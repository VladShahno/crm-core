package com.crm.verification.core.dto.request.create;

import static com.crm.verification.core.common.Constants.CoreServiceValidation.VERIFICATION_RESULT_REQUIRED;
import static com.crm.verification.core.common.Constants.VerificationResult.N_A_ADDRESS;
import static com.crm.verification.core.common.Constants.VerificationResult.N_A_COMPANY;
import static com.crm.verification.core.common.Constants.VerificationResult.N_A_EMPLOYEES_SIZE;
import static com.crm.verification.core.common.Constants.VerificationResult.N_A_INDUSTRY;
import static com.crm.verification.core.common.Constants.VerificationResult.N_A_REVENUE;
import static com.crm.verification.core.common.Constants.VerificationResult.N_A_TITLE;
import static com.crm.verification.core.common.Constants.VerificationResult.Y2_ACCEPTED;
import static com.crm.verification.core.common.Constants.VerificationResult.Y3_ACCEPTED;
import static com.crm.verification.core.common.Constants.VerificationResult.Y4_ACCEPTED;
import static com.crm.verification.core.common.Constants.VerificationResult.Y_ACCEPTED;

import javax.validation.constraints.NotBlank;

import com.crm.verification.core.validation.OneOfS;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerificationResultRequestDto {

  @NotBlank(message = VERIFICATION_RESULT_REQUIRED)
  @OneOfS(values = {
      Y_ACCEPTED, Y2_ACCEPTED, Y3_ACCEPTED, Y4_ACCEPTED, N_A_COMPANY, N_A_TITLE, N_A_EMPLOYEES_SIZE,
      N_A_REVENUE, N_A_INDUSTRY, N_A_ADDRESS
  })
  String result;
}
