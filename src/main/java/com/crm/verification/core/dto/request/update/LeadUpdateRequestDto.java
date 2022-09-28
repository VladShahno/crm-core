package com.crm.verification.core.dto.request.update;

import static com.crm.verification.core.common.Constants.CoreServiceValidation.COMMENTS_MAX_LENGTH;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.FIRST_NAME_IS_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.LAST_NAME_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.PROOF_LINK_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.TITLE_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.VERIFICATION_RESULT_REQUIRED;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.crm.verification.core.validation.ValidateSpecialCharacters;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeadUpdateRequestDto {

  @NotBlank(message = VERIFICATION_RESULT_REQUIRED)
  String verificationResults;

  @ValidateSpecialCharacters
  @NotBlank(message = FIRST_NAME_IS_REQUIRED)
  private String firstName;

  @ValidateSpecialCharacters
  @NotBlank(message = LAST_NAME_REQUIRED)
  private String lastName;

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
  private CompanyUpdatedRequestDto company;
}
