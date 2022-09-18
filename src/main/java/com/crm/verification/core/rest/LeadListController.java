package com.crm.verification.core.rest;

import javax.validation.constraints.NotBlank;

import com.crm.verification.core.dto.response.list.LeadListResponseDto;
import com.crm.verification.core.dto.response.profile.LeadProfileResponseDto;
import com.crm.verification.core.service.LeadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
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
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/v1/leads-list", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(name = "Lead List Controller", description = "Provides general info about leads' data in package")
public class LeadListController {

  private final LeadService leadService;

  @GetMapping(value = "/{packageId}")
  @ResponseStatus(HttpStatus.OK)
  @PageableAsQueryParam
  @Operation(summary = "Endpoint allows to get leads by packageId")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Leads received successfully",
          content = {@Content(schema = @Schema(implementation = LeadProfileResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Bad Request",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "404", description = "Not Found",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})
  })
  public Page<LeadListResponseDto> getAllLeadsByPackageId(
      @Parameter(description = "Leads packageId", required = true, example = "f93h494j4k33h5")
      @PathVariable(value = "packageId")
      @NotBlank(message = "{not.blank}")
      String packageId,
      @PageableDefault(sort = "firstName", size = 25) Pageable pageable) {
    return leadService.getAllLeadsByPackageId(packageId, pageable);
  }
}
