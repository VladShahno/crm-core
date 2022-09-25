package com.crm.verification.core.dto.response.list;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressListResponseDto {

  private Long id;
  private String country;
  private String city;
}
