package com.crm.verification.core.rest;

import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.crm.verification.core.dto.request.LeadRequestDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/profile")
@Validated
@Tag(name = "Lead profile Controller", description = "Provides CRUD operations for lead profile content")
public class LeadProfileController {

  private LeadService leadService;

  @GetMapping(value = "/{email}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Endpoint allows to get lead profile by email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lead profile received successfully",
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
  public LeadProfileResponseDto getLeadProfileByEmail(
      @Parameter(description = "Target lead's email", example = "john.smith@gmail.com")
      @PathVariable @NotBlank(message = "{not.blank}") @Email String email) {
    return leadService.getLeadProfileByEmail(email);
  }

  @PostMapping("/create-profile")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Endpoint allows to create lead profile")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created lead profile",
          content = {@Content(schema = @Schema(implementation = LeadListResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Bad Request",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})
  })
  public LeadListResponseDto createLead(
      @RequestBody @Valid LeadRequestDto leadRequestDto,
      @Parameter(description = "PackageId to which the lead will be assigned", example = "ccSooifMMSyVt5FeyQfw")
      @RequestParam(value = "packageId") @NotBlank(message = "{not.blank}") String packageId) {
    return leadService.createLeadProfile(leadRequestDto, packageId);
  }
}
