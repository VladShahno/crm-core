package com.crm.verification.core.dto.response.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressListResponseDto {

  private Long id;
  private String country;
  private String city;
}
