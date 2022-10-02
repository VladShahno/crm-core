package com.crm.verification.core.rest;

import static com.crm.verification.core.common.Constants.CoreServiceValidation.COMPANY_NAME_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.EMAIL_REQUIRED;
import static com.crm.verification.core.common.Constants.CoreServiceValidation.PACKAGE_NAME_REQUIRED;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.crm.verification.core.dto.request.create.LeadCreateRequestDto;
import com.crm.verification.core.dto.request.update.LeadUpdateRequestDto;
import com.crm.verification.core.dto.response.exception.ExceptionResponse;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/profile")
@Validated
@Tag(name = "Lead profile Controller", description = "Provides CRUD operations for lead profile content")
public class LeadProfileController {

  private LeadService leadService;

  @GetMapping(value = "/{email}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Endpoint allows to get lead's profile by email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lead profile received successfully",
          content = {@Content(schema = @Schema(implementation = LeadProfileResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Bad Request",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "404", description = "Not Found",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))})
  })
  public LeadProfileResponseDto getLeadProfileByEmail(
      @Parameter(description = "Target lead email", example = "john.smith@gmail.com")
      @NotBlank(message = EMAIL_REQUIRED) @PathVariable @Email String email) {
    return leadService.getLeadProfileByEmail(email);
  }

  @PostMapping("/create-profile")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Endpoint allows to create lead's profile")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created lead profile",
          content = {@Content(schema = @Schema(implementation = LeadListResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Bad Request",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))})
  })
  public LeadListResponseDto createLead(
      @RequestBody @Valid LeadCreateRequestDto leadRequestDto,
      @Parameter(description = "Package name to which the lead will be assigned", example = "Pack")
      @NotBlank(message = PACKAGE_NAME_REQUIRED) @RequestParam(value = "packageName") String packageName) {
    return leadService.createLeadProfile(leadRequestDto, packageName);
  }

  @PatchMapping(value = "/update/{email}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Endpoint allows to update lead's profile by email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully updated lead profile",
          content = {@Content(schema = @Schema(implementation = LeadProfileResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Bad Request",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))})
  })
  public LeadProfileResponseDto updateLeadProfileByEmail(
      @Parameter(description = "Lead's email to update", example = "john.smith@gmail.com")
      @NotBlank(message = EMAIL_REQUIRED) @Email @PathVariable(value = "email")
      String email,
      @Parameter(description = "Lead package name", example = "Pack")
      @NotBlank(message = PACKAGE_NAME_REQUIRED) @RequestParam(value = "packageName")
      String packageName,
      @RequestBody @Valid
      LeadUpdateRequestDto leadUpdateRequestDto) {
    return leadService.updateLeadProfileByEmailAndPackageName(packageName, email, leadUpdateRequestDto);
  }

  @DeleteMapping(value = "/delete/{email}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Endpoint allows to delete lead's profile by email")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Successfully delete lead profile"),
      @ApiResponse(responseCode = "400", description = "Bad Request",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))})
  })
  public void deleteLeadProfileByEmail(
      @Parameter(description = "Lead email to delete", example = "john.smith@gmail.com")
      @NotBlank(message = EMAIL_REQUIRED) @Email @PathVariable(value = "email")
      String email) {
    leadService.deleteLeadProfileByEmail(email);
  }

  @PatchMapping(value = "/update/company/{email}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Endpoint allows to change lead company by email and new existing company name. "
      + "Old company will be unassigned and new will be assigned.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully updated company",
          content = {@Content(schema = @Schema(implementation = LeadProfileResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Bad Request",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = {@Content(schema = @Schema(implementation = ExceptionResponse.class))})
  })
  public LeadProfileResponseDto changeLeadCompany(
      @Parameter(description = "Lead email to change company", example = "john.smith@gmail.com")
      @NotBlank(message = EMAIL_REQUIRED) @Email @PathVariable(value = "email")
      String leadEmail,
      @Parameter(description = "Existing company name to be assigned to the lead", example = "newSoft")
      @NotBlank(message = COMPANY_NAME_REQUIRED) @RequestParam(value = "companyName")
      String companyName) {
    return leadService.changeLeadCompany(companyName, leadEmail);
  }
}
