package com.crm.verification.core.rest;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.crm.verification.core.dto.request.LeadRequestDto;
import com.crm.verification.core.dto.response.list.LeadListResponseDto;
import com.crm.verification.core.dto.response.profile.LeadProfileResponseDto;
import com.crm.verification.core.service.LeadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class LeadProfileController {

  private LeadService leadService;

  @GetMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Lead profile has got successfully",
          content = {@Content(schema = @Schema(implementation = LeadProfileResponseDto.class))}),
      @ApiResponse(responseCode = "404", description = "Invalid params supplied",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})
  })
  public LeadProfileResponseDto getLeadProfileById(@PathVariable @NotBlank(message = "{not.blank}") String id) {
    return leadService.getLeadProfileByEmail(id);
  }

  @PostMapping("/create-profile")
  @ResponseStatus(HttpStatus.CREATED)
  @Operation(summary = "Create lead profile")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Successfully created lead profile",
          content = {@Content(schema = @Schema(implementation = LeadListResponseDto.class))}),
      @ApiResponse(responseCode = "401", description = "Invalid params supplied",
          content = {@Content(schema = @Schema(implementation = ResponseStatusException.class))})
  })
  public LeadListResponseDto createLead(
      @RequestBody @NotNull LeadRequestDto leadRequestDto,
      @RequestParam(value = "packageIds") @NotNull Set<String> packageIds) {
    return leadService.createLeadProfile(leadRequestDto, packageIds);
  }
}
