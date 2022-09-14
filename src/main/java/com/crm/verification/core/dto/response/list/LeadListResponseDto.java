package com.crm.verification.core.dto.response.list;

import java.util.List;

import com.crm.verification.core.model.Lead;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class LeadListResponseDto {

  private CompanyListResponseDto company;
  private List<PackageDataListResponseDto> packageData;
  private String firstName;
  private String lastName;
  private String email;
  private String title;
  private String proofLink;
  private String verdict;

  public LeadListResponseDto(Lead lead) {
    BeanUtils.copyProperties(lead, this);
  }
}
