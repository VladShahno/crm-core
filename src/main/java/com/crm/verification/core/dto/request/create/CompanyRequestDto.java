package com.crm.verification.core.dto.request.create;

import static com.crm.verification.core.common.Constants.CoreServiceValidation.COMMENTS_MAX_LENGTH;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.COMPANY_NAME_REQUIRED;
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
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDto {

  @ValidateSpecialCharacters
  @NotBlank(message = COMPANY_NAME_REQUIRED)
  private String name;

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

  @JsonIgnore
  private Set<LeadRequestDto> leads = new HashSet<>();

  @Valid
  private Set<AddressRequestDto> addresses = new HashSet<>();
}
