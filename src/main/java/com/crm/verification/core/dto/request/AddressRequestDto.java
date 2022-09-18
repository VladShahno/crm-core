package com.crm.verification.core.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDto {

  private CompanyRequestDto company;
  private String country;
  private String street;
  private String city;
  private String state;
  private String postalCode;
  private String phoneNumber;
}
