package com.crm.verification.core.dto.request.update;

import static com.crm.verification.core.common.Constants.CoreServiceValidation.COMMENTS_MAX_LENGTH;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.EMPLOYEES_PROOF_LINK_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.EMPLOYEES_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.INDUSTRY_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.REVENUE_PROOF_LINK_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.REVENUE_REQUIRED;

import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.crm.verification.core.validation.ValidateSpecialCharacters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyUpdatedRequestDto {

  @ValidateSpecialCharacters
  @NotBlank(message = INDUSTRY_REQUIRED)
  private String industry;

  @ValidateSpecialCharacters
  @NotBlank(message = EMPLOYEES_REQUIRED)
  private String employees;

  @ValidateSpecialCharacters
  @NotBlank(message = EMPLOYEES_PROOF_LINK_REQUIRED)
  private String employeesProofLink;

  @ValidateSpecialCharacters
  @NotBlank(message = REVENUE_REQUIRED)
  private String revenue;

  @ValidateSpecialCharacters
  @NotBlank(message = REVENUE_PROOF_LINK_REQUIRED)
  private String revenueProofLink;

  @ValidateSpecialCharacters
  @Size(max = 300, message = COMMENTS_MAX_LENGTH)
  private String companyComments;

  @Valid
  private Set<AddressUpdateRequestDto> addresses = new HashSet<>();
}
