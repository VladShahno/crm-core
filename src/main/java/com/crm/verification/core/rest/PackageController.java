package com.crm.verification.core.rest;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.crm.verification.core.dto.response.packagedata.PackageDataResponseDto;
import com.crm.verification.core.service.PackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/v1/packages")
@RequiredArgsConstructor
@Validated
@Tag(name = "Package Controller", description = "Provides CRUD operations for package content")
public class PackageController {

  private final PackageService packageService;

  @PostMapping(value = "/create-package")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Endpoint allows to create package")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created package",
          content = {@Content(schema = @Schema(implementation = PackageDataResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "Bad Request",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "403", description = "Forbidden",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))}),
      @ApiResponse(responseCode = "500", description = "Internal Server Error",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})
  })
  public PackageDataResponseDto createPackage(
      @Parameter(description = "Package name for creating", example = "NewSoft")
      @RequestParam
      @NotBlank(message = "{not.blank}") String packageName,
      @Parameter(description = "Leads' emails to add to new package", example = "NewSoft")
      @RequestParam(value = "leadEmails", required = false)
      Set<@NotBlank(message = "{not.blank}") String> leadEmails) {
    return packageService.createPackage(packageName, leadEmails);
  }

  @GetMapping(value = "/by-package-name/{packageName}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Endpoint allows to get package by packageName")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Package received successfully",
          content = {@Content(schema = @Schema(implementation = PackageDataResponseDto.class))}),
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
  public List<PackageDataResponseDto> getAllPackagesByPackageName(
      @Parameter(description = "Target packageName", example = "ccSooifMMSyVt5FeyQfw")
      @NotBlank(message = "{not.blank}") @PathVariable(value = "packageName") String packageName) {
    return packageService.getAllPackagesByPackageName(packageName);
  }

  @PatchMapping(value = "/add-to-package/{packageName}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Endpoint allows to add leads to package")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Leads added to package successfully",
          content = {@Content(schema = @Schema(implementation = PackageDataResponseDto.class))}),
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
  public PackageDataResponseDto addLeadsToPackage(
      @Parameter(description = "Leads' emails  to add to the package", example = "[john.smith@gmail.com,john.snow@gmail.com]")
      @RequestBody @Valid Set<@Email String> leadEmails,
      @Parameter(description = "Target packageName to which leads will be added", example = "MicroSoft")
      @PathVariable(value = "packageName") @NotBlank(message = "{not.blank}") String packageName) {
    return packageService.addExistingLeadsToExistingPackage(packageName, leadEmails);
  }
}
