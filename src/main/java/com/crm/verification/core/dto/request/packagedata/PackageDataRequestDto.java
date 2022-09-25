package com.crm.verification.core.dto.request.packagedata;

import com.crm.verification.core.entity.PackageData;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class PackageDataRequestDto {

  private String packageName;

  public PackageDataRequestDto(PackageData packageData) {
    BeanUtils.copyProperties(packageData, this);
  }
}
