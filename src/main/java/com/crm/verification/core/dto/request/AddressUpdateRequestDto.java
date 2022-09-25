package com.crm.verification.core.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressUpdateRequestDto {

  private Long id;
  @JsonIgnore
  private CompanyRequestDto company;
  private String country;
  private String street;
  private String city;
  private String state;
  private String postalCode;
  private String phoneNumber;
}
