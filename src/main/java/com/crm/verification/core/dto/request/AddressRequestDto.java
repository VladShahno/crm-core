package com.crm.verification.core.dto.request;

import java.util.Date;

import com.crm.verification.core.model.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressRequestDto {

  @JsonIgnore
  private Company company;

  private String country;
  private String street;
  private String city;
  private String state;
  private String postalCode;
  private String phoneNumber;
  private Date created;
}
