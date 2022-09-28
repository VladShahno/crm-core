package com.crm.verification.core.dto.response.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressProfileResponseDto {

  private Long id;
  private String country;
  private String street;
  private String city;
  private String state;
  private String postalCode;
  private String phoneNumber;
}
