package com.crm.verification.core.rest;

import javax.validation.constraints.NotBlank;

import com.crm.verification.core.dto.response.list.LeadListResponseDto;
import com.crm.verification.core.service.LeadService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/leads-list", produces = MediaType.APPLICATION_JSON_VALUE)
public class LeadListController {

  private final LeadService leadService;

  @GetMapping(value = "/{packageId}")
  public Page<LeadListResponseDto> getAllLeadsByPackageId(
      @Parameter(description = "Leads packageId", required = true, example = "f93h494j4k33h5")
      @PathVariable(value = "packageId")
      @NotBlank(message = "{not.blank}")
      String packageId,
      @PageableDefault(sort = "firstName", size = 25) Pageable pageable) {
    return leadService.getAllLeadsByPackageId(packageId, pageable);
  }
}
