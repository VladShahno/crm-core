package com.crm.verification.core.rest;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.crm.verification.core.dto.request.LeadRequestDto;
import com.crm.verification.core.dto.response.list.LeadListResponseDto;
import com.crm.verification.core.model.Lead;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1/profile")
public class LeadProfileController {

  private LeadService leadService;

  @GetMapping(value = "/{id}")
  @ResponseBody
  public Lead getLeadProfileById(@PathVariable @NotBlank(message = "{not.blank}") Long id) {
    return leadService.getById(id);
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
      @NotNull List<String> packageIds) {
    return leadService.createLead(leadRequestDto, packageIds);
  }
}
