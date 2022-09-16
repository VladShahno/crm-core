package com.crm.verification.core.rest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;

import com.crm.verification.core.dto.request.packagedata.PackageDataRequestDto;
import com.crm.verification.core.dto.response.packagedata.PackageDataResponseDto;
import com.crm.verification.core.service.PackageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/v1/packages")
@RequiredArgsConstructor
public class PackageController {

  private final PackageService packageService;

  @PostMapping(value = "/create-package")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create new package")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully package",
          content = {@Content(schema = @Schema(implementation = PackageDataResponseDto.class))}),
      @ApiResponse(responseCode = "401", description = "Invalid params supplied",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})
  })
  public PackageDataResponseDto createPackage(@RequestBody @NotNull PackageDataRequestDto packageDataRequestDto) {
    return packageService.createPackage(packageDataRequestDto);
  }

  @GetMapping(value = "/by-package-id")
  @ResponseStatus(HttpStatus.OK)
  @Operation(summary = "Get package by packageId")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Package has got successfully",
          content = {@Content(schema = @Schema(implementation = PackageDataResponseDto.class))}),
      @ApiResponse(responseCode = "404", description = "Package not found",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})
  })
  public Page<PackageDataResponseDto> getAllByPackageId(
      @NotBlank(message = "{not.blank}")
      @PathParam("packageId") String packageId,
      @PageableDefault(sort = "packageName", size = 25) Pageable pageable) {
    return packageService.getAllPackagesByPackageId(packageId, pageable);
  }
}
