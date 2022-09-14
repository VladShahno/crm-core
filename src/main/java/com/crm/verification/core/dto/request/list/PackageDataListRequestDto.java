package com.crm.verification.core.dto.request.list;

import java.util.List;

import com.crm.verification.core.dto.request.LeadRequestDto;
import com.crm.verification.core.model.PackageData;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class PackageDataListRequestDto {
  private String packageName;
  private String packageId;
  private List<LeadRequestDto> leads;

  public PackageDataListRequestDto(PackageData packageData) {
    BeanUtils.copyProperties(packageData, this);
  }
}
