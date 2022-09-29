package com.crm.verification.core.dto.request.create;

import static com.crm.verification.core.common.Constants.CoreServiceValidation.COMMENTS_MAX_LENGTH;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.FIRST_NAME_IS_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.LAST_NAME_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.PROOF_LINK_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.TITLE_REQUIRED;
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

import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.crm.verification.core.validation.OneOfS;
import com.crm.verification.core.validation.ValidateSpecialCharacters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadCreateRequestDto {

//  @NotBlank(message = VERIFICATION_RESULT_REQUIRED)
//  @OneOfS(values = {
//      Y_ACCEPTED, Y2_ACCEPTED, Y3_ACCEPTED, Y4_ACCEPTED, N_A_COMPANY, N_A_TITLE, N_A_EMPLOYEES_SIZE,
//      N_A_REVENUE, N_A_INDUSTRY, N_A_ADDRESS
//  })
  String verificationResults;

  @ValidateSpecialCharacters
  @NotBlank(message = FIRST_NAME_IS_REQUIRED)
  private String firstName;

  @ValidateSpecialCharacters
  @NotBlank(message = LAST_NAME_REQUIRED)
  private String lastName;

  @Email
  private String email;

  @ValidateSpecialCharacters
  @NotBlank(message = TITLE_REQUIRED)
  private String title;

  @ValidateSpecialCharacters
  @NotBlank(message = PROOF_LINK_REQUIRED)
  private String proofLink;

  @ValidateSpecialCharacters
  @Size(max = 300, message = COMMENTS_MAX_LENGTH)
  private String leadComments;

  @Valid
  private CompanyRequestDto company;
}
