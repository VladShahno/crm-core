package com.crm.verification.core.dto.request.update;

import static com.crm.verification.core.common.Constants.CoreServiceValidation.ADDRESS_ID_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.CITY_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.COUNTRY_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.PHONE_NUMBER_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.POSTAL_CODE_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.STATE_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.STREET_REQUIRED;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.crm.verification.core.validation.ValidateSpecialCharacters;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateRequestDto {

  @NotNull(message = ADDRESS_ID_REQUIRED)
  private Long id;

  @ValidateSpecialCharacters
  @NotBlank(message = COUNTRY_REQUIRED)
  private String country;

  @ValidateSpecialCharacters
  @NotBlank(message = STREET_REQUIRED)
  private String street;

  @ValidateSpecialCharacters
  @NotBlank(message = CITY_REQUIRED)
  private String city;

  @ValidateSpecialCharacters
  @NotBlank(message = STATE_REQUIRED)
  private String state;

  @ValidateSpecialCharacters
  @NotBlank(message = POSTAL_CODE_REQUIRED)
  private String postalCode;

  @ValidateSpecialCharacters
  @NotBlank(message = PHONE_NUMBER_REQUIRED)
  private String phoneNumber;
}
