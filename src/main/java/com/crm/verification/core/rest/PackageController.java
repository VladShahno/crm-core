package com.crm.verification.core.rest;

import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
      @NotBlank(message = "{not.blank}") String packageName) {
    return packageService.createPackage(packageName);
  }

  @GetMapping(value = "/by-package-id/{packageId}")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Endpoint allows to get package by packageId")
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
  public List<PackageDataResponseDto> getAllByPackageId(
      @Parameter(description = "Target packageId", example = "ccSooifMMSyVt5FeyQfw")
      @NotBlank(message = "{not.blank}") @PathVariable(value = "packageId") String packageId) {
    return packageService.getAllPackagesByPackageId(packageId);
  }
}
