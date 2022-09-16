package com.crm.verification.core.service;

import static com.crm.verification.core.common.Constants.Logging.EMAIL;
import static com.crm.verification.core.common.Constants.Logging.ID;
import static com.crm.verification.core.common.Constants.Logging.LEAD_NOT_FOUND;
import static com.crm.verification.core.common.Constants.Logging.PACKAGE_IDS;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;
import java.util.stream.Collectors;

import com.crm.verification.core.dto.request.LeadRequestDto;
import com.crm.verification.core.dto.response.list.LeadListResponseDto;
import com.crm.verification.core.exception.ResourceExistsException;
import com.crm.verification.core.exception.ResourceNotFoundException;
import com.crm.verification.core.mapper.LeadMapper;
import com.crm.verification.core.mapper.PackageDataMapper;
import com.crm.verification.core.model.Lead;
import com.crm.verification.core.repository.LeadRepository;
import com.crm.verification.core.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Setter
@Service
@RequiredArgsConstructor
public class LeadService {

  private final LeadRepository leadRepository;
  private final PackageRepository packageRepository;
  private final LeadMapper leadMapper;
  private final PackageDataMapper packageDataMapper;

  public LeadListResponseDto createLead(LeadRequestDto leadDto, List<String> packageIds) {

    if (leadRepository.existsByEmail(leadDto.getEmail())) {
      log.error("Lead with {} already exists", keyValue(EMAIL, leadDto.getEmail()));
      throw new ResourceExistsException(EMAIL + leadDto.getEmail());
    }

    var packages = packageRepository.findAllByPackageIdIn(packageIds);
    if (CollectionUtils.isNotEmpty(packages)) {
      log.debug("Setting packageData with {}", keyValue(PACKAGE_IDS, packageIds));
      leadDto.setPackageData(packages
          .stream()
          .map(packageDataMapper::toPackageDataListRequestDto)
          .collect(Collectors.toSet()));

      var lead = leadMapper.toLeadEntity(leadDto);
      packages.forEach(packageData -> packageData.addLeads(lead));
      leadRepository.save(lead);

      return leadMapper.toLeadListResponseDto(lead);
    }
    throw new ResourceNotFoundException(PACKAGE_IDS + packageIds);
  }

  public Lead updateLeadById(Long id, LeadRequestDto leadRequestDto) {
    var lead = leadRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(ID + id));
    if (leadRepository.existsByEmail(leadRequestDto.getEmail())
        && !leadRequestDto.getEmail().equals(lead.getEmail())) {
      log.error("Can't update lead with existing {}", keyValue(EMAIL, leadRequestDto.getEmail()));
      throw new ResourceExistsException(EMAIL + leadRequestDto.getEmail());
    }

    log.debug("Updating lead with {}", keyValue(ID, id));
    var updatedLead = leadMapper.toLeadEntity(leadRequestDto);
    updatedLead.setId(id);
    return leadRepository.save(updatedLead);
  }

  public void deleteLeadById(Long id) {
    leadRepository.findById(id).ifPresentOrElse(lead -> {
      log.debug("Deleting lead with {}", keyValue(ID, id));
      leadRepository.deleteById(id);
    }, () -> {
      log.error(LEAD_NOT_FOUND, keyValue(ID, id));
      throw new ResourceNotFoundException(ID + id);
    });
  }

  public Lead getById(Long id) {
    log.debug("Getting lead with {}", keyValue(ID, id));
    return leadRepository.findById(id)
        .orElseThrow(() -> {
          log.error(LEAD_NOT_FOUND, keyValue(ID, id));
          return new ResourceNotFoundException(ID + id);
        });
  }

  public Page<LeadListResponseDto> getAllLeadsByPackageId(String packageId, Pageable pageable) {
    return leadRepository.findAllByPackageDataPackageId(packageId, pageable)
        .map(leadMapper::toLeadListResponseDto);
  }
}
