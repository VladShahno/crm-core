package com.crm.verification.core.dto.response.profile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressProfileResponseDto {

  private String country;
  private String street;
  private String city;
  private String state;
  private String postalCode;
  private String phoneNumber;
}
