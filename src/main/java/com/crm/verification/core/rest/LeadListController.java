package com.crm.verification.core.rest;

import static com.crm.verification.core.common.Constants.CoreServiceValidation.PACKAGE_NAME_REQUIRED;

import javax.validation.constraints.NotBlank;

import com.crm.verification.core.dto.response.list.LeadListResponseDto;
import com.crm.verification.core.service.LeadService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/leads-list", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class LeadListController {

  private final LeadService leadService;

  @GetMapping(value = "/{packageName}")
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation("Endpoint allows to get leads by packageName")
  public Page<LeadListResponseDto> getAllLeadsByPackageName(
      @PathVariable(value = "packageName")
      @NotBlank(message = PACKAGE_NAME_REQUIRED)
      String packageName,
      @PageableDefault(sort = "firstName", size = 25) Pageable pageable) {
    return leadService.getAllLeadsByPackageNameWithAppropriateResult(packageName, pageable);
  }
}
