package com.crm.verification.core.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.crm.verification.core.dto.request.create.CompanyRequestDto;
import com.crm.verification.core.dto.request.update.CompanyUpdatedRequestDto;
import com.crm.verification.core.dto.response.company.CompanyAllResponseDto;
import com.crm.verification.core.dto.response.company.CompanyCreateResponseDto;
import com.crm.verification.core.dto.response.company.CompanyProfileResponseDto;
import com.crm.verification.core.dto.response.profile.LeadProfileResponseDto;
import com.crm.verification.core.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/v1/companies")
@RequiredArgsConstructor
@Validated
@Tag(name = "Company Controller", description = "Provides CRUD operations for company content")
public class CompanyController {

  private final CompanyService companyService;

  @GetMapping(value = "/all")
  @ResponseStatus(HttpStatus.OK)
  @PageableAsQueryParam
  @Operation(summary = "Endpoint allows to get all companies")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Companies received successfully",
          content = {@Content(schema = @Schema(implementation = CompanyAllResponseDto.class))}),
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
  public Page<CompanyAllResponseDto> getAllCompanies(@PageableDefault(sort = "name", size = 25) Pageable pageable) {
    return companyService.getAllCompanies(pageable);
  }

  @PostMapping("/create-company")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Endpoint allows to create company")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created company",
          content = {@Content(schema = @Schema(implementation = CompanyCreateResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Bad Request",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})
  })
  public CompanyCreateResponseDto createCompany(@RequestBody @Valid CompanyRequestDto companyDto) {
    return companyService.createCompany(companyDto);
  }

  @DeleteMapping(value = "/delete{companyName}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @Operation(summary = "Endpoint allows to delete company by name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Successfully delete lead profile",
          content = {@Content(schema = @Schema(implementation = LeadProfileResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Bad Request",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})
  })
  public void deleteCompanyByName(
      @Parameter(description = "Company name to delete", example = "NewSoft")
      @PathVariable(value = "companyName") @NotBlank(message = "{not.blank}") String companyName) {
    companyService.deleteCompanyByName(companyName);
  }

  @GetMapping(value = "/by-company-name/{companyName}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Endpoint allows to get company by name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Company received successfully",
          content = {@Content(schema = @Schema(implementation = CompanyAllResponseDto.class))}),
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
  public CompanyAllResponseDto getCompanyByName(
      @Parameter(description = "Target company name", example = "NewSoft")
      @PathVariable(value = "companyName") @NotBlank(message = "{not.blank}") String companyName) {
    return companyService.getCompanyByName(companyName);
  }

  @PatchMapping(value = "/update/{companyName}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Endpoint allows to update company by name")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully updated company",
          content = {@Content(schema = @Schema(implementation = CompanyProfileResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Bad Request",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})
  })
  public CompanyProfileResponseDto updateCompanyByName(
      @Parameter(description = "Target company name to update", example = "NewSoft")
      @PathVariable(value = "companyName") @NotBlank(message = "{not.blank}")
      String companyName,
      @RequestBody @Valid CompanyUpdatedRequestDto companyDto) {
    return companyService.updateCompanyByName(companyName, companyDto);
  }
}
