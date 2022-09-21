package com.crm.verification.core.service;

import static com.crm.verification.core.common.Constants.Logging.EMAIL;
import static com.crm.verification.core.common.Constants.Logging.ID;
import static com.crm.verification.core.common.Constants.Logging.LEAD_NOT_FOUND;
import static com.crm.verification.core.common.Constants.Logging.PACKAGE_IDS;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.crm.verification.core.dto.request.LeadRequestDto;
import com.crm.verification.core.dto.response.list.LeadListResponseDto;
import com.crm.verification.core.dto.response.profile.LeadProfileResponseDto;
import com.crm.verification.core.exception.ResourceExistsException;
import com.crm.verification.core.exception.ResourceNotFoundException;
import com.crm.verification.core.mapper.LeadMapper;
import com.crm.verification.core.mapper.PackageDataMapper;
import com.crm.verification.core.model.Lead;
import com.crm.verification.core.model.PackageData;
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
@Transactional
public class LeadService {

  private final LeadRepository leadRepository;
  private final PackageRepository packageRepository;
  private final LeadMapper leadMapper;
  private final PackageDataMapper packageDataMapper;

  @Transactional
  public LeadListResponseDto createLeadProfile(LeadRequestDto leadDto, Set<String> packageIds) {

    if (leadRepository.existsByEmail(leadDto.getEmail())) {
      log.error("Lead with {} already exists", keyValue(EMAIL, leadDto.getEmail()));
      throw new ResourceExistsException(EMAIL + leadDto.getEmail());
    }
    return saveLeadWithBiDirectionalRelation(packageIds, leadDto);
  }

  public Lead updateLeadProfileByEmail(String id, LeadRequestDto leadRequestDto) {
    var lead = leadRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(ID + id));
    if (leadRepository.existsByEmail(leadRequestDto.getEmail())
        && !leadRequestDto.getEmail().equals(lead.getEmail())) {
      log.error("Can't update lead with existing {}", keyValue(EMAIL, leadRequestDto.getEmail()));
      throw new ResourceExistsException(EMAIL + leadRequestDto.getEmail());
    }

    log.debug("Updating lead with {}", keyValue(ID, id));
    var updatedLead = leadMapper.toLeadEntity(leadRequestDto);
    //updatedLead.setId(id);
    return leadRepository.save(updatedLead);
  }

  public void deleteLeadProfileByEmail(String email) {
    leadRepository.findById(email).ifPresentOrElse(lead -> {
      log.debug("Deleting lead with {}", keyValue(EMAIL, email));
      leadRepository.deleteById(email);
    }, () -> {
      log.error(LEAD_NOT_FOUND, keyValue(EMAIL, email));
      throw new ResourceNotFoundException(EMAIL + email);
    });
  }

  public LeadProfileResponseDto getLeadProfileByEmail(String email) {
    log.debug("Getting lead with {}", keyValue(EMAIL, email));
    var lead = leadRepository.findById(email)
        .orElseThrow(() -> {
          log.error(LEAD_NOT_FOUND, keyValue(EMAIL, email));
          return new ResourceNotFoundException(EMAIL + email);
        });
    return leadMapper.toLeadProfileResponseDto(lead);
  }

  public Page<LeadListResponseDto> getAllLeadsByPackageId(String packageId, Pageable pageable) {

    Page<Lead> leadsList = leadRepository.findAllByPackageDataPackageId(packageId, pageable);

    leadsList.stream().forEach(lead -> lead.setVerificationResults(
        lead.getVerificationResults().stream().filter(verificationResult -> verificationResult.getPackageData().getPackageId().equals(packageId))
            .collect(Collectors.toSet())));

    return leadsList.map(leadMapper::toLeadListResponseDto);
  }

  private LeadListResponseDto saveLeadWithBiDirectionalRelation(Set<String> packageIds, LeadRequestDto leadDto) {
    var packages = new HashSet<>(packageRepository.findAllByPackageIdIn(packageIds));
    if (CollectionUtils.isNotEmpty(packages)) {
      log.debug("Setting packageData with {}", keyValue(PACKAGE_IDS, packageIds));
      var leadToSave = leadMapper.toLeadEntity(leadDto);

      return setBiDirectionalRelation(packages, leadToSave);
    }
    log.error("{} not found", keyValue(PACKAGE_IDS, packageIds));
    throw new ResourceNotFoundException(PACKAGE_IDS + packageIds);
  }

  private LeadListResponseDto setBiDirectionalRelation(HashSet<PackageData> packages, Lead lead) {

    lead.setPackageData(packages);

    lead.getVerificationResults().forEach(verificationResult -> {
      verificationResult.setLead(lead);
      verificationResult.setPackageData(packages.stream().findFirst().get());
    });

    packages.forEach(packageData -> packageData.addLeads(lead));
    lead.getCompany().getAddresses()
        .forEach(address -> address.setCompany(lead.getCompany()));

    leadRepository.save(lead);

    return leadMapper.toLeadListResponseDto(lead);
  }
}
