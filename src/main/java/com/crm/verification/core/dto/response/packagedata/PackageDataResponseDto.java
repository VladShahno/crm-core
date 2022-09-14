package com.crm.verification.core.dto.response.packagedata;

import java.util.List;

import com.crm.verification.core.model.PackageData;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class PackageDataResponseDto {

  private String packageName;
  private String packageId;
  private List<LeadPackageResponseDto> leads;

  public PackageDataResponseDto(PackageData packageData) {
    BeanUtils.copyProperties(packageData, this);
  }
}
