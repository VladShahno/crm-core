package com.crm.verification.core.service;

import static com.crm.verification.core.common.Constants.Logging.EMAIL;
import static com.crm.verification.core.common.Constants.Logging.LEAD_NOT_FOUND;
import static com.crm.verification.core.common.Constants.Logging.NOT_FOUND_ERROR_KEY_VALUE;
import static com.crm.verification.core.common.Constants.Logging.PACKAGE_NAME;
import static com.crm.verification.core.common.Constants.Logging.VERIFICATION_RESULT;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.crm.verification.core.dto.request.LeadRequestDto;
import com.crm.verification.core.dto.request.LeadUpdateRequestDto;
import com.crm.verification.core.dto.response.list.LeadListResponseDto;
import com.crm.verification.core.dto.response.profile.LeadProfileResponseDto;
import com.crm.verification.core.entity.Lead;
import com.crm.verification.core.entity.PackageData;
import com.crm.verification.core.entity.VerificationResult;
import com.crm.verification.core.exception.ResourceExistsException;
import com.crm.verification.core.exception.ResourceNotFoundException;
import com.crm.verification.core.mapper.LeadMapper;
import com.crm.verification.core.repository.LeadRepository;
import com.crm.verification.core.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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

  @Transactional
  public LeadListResponseDto createLeadProfile(LeadRequestDto leadDto, String packageName) {
    if (leadRepository.existsByEmail(leadDto.getEmail())) {
      log.error("Lead with {} already exists", keyValue(EMAIL, leadDto.getEmail()));
      throw new ResourceExistsException(EMAIL + leadDto.getEmail());
    }
    return setBiDirectionalRelationToLeadWithSaving(packageName, leadDto);
  }

  public LeadProfileResponseDto updateLeadProfileByEmailAndPackageName(
      String packageName,
      String email,
      LeadUpdateRequestDto leadRequestDto) {

    var leadByEmail = findLeadByEmailAndPackageName(email, packageName);
    var verificationResult = updateVerificationResult(leadByEmail.getVerificationResults(), packageName,
        leadRequestDto.getVerificationResults());

    var updatedLead = leadMapper.toLeadEntity(leadRequestDto);
    updatedLead.setEmail(email);
    updatedLead.getCompany().setName(leadByEmail.getCompany().getName());
    updatedLead.getCompany().getAddresses().forEach(address -> address.setCompany(updatedLead.getCompany()));
    updatedLead.addVerificationResult(verificationResult);

    log.debug("Updating lead with {}", keyValue(EMAIL, email));
    return leadMapper.toLeadProfileResponseDto(leadRepository.save(updatedLead));
  }

  @Transactional
  public void deleteLeadProfileByEmail(String email) {
    leadRepository.findByEmail(email).ifPresentOrElse(lead -> {
      log.debug("Deleting lead with {}", keyValue(EMAIL, email));
      lead.getPackageData().forEach(packageData -> packageData.removeLead(lead));
      leadRepository.deleteByEmail(email);
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

  public Page<LeadListResponseDto> getAllLeadsByPackageNameWithAppropriateResult(
      String packageName,
      Pageable pageable) {

    var leads = leadRepository.findAllByPackageDataPackageName(packageName, pageable);

    leads.stream().forEach(lead -> lead.setVerificationResults(
        lead.getVerificationResults().stream()
            .filter(verificationResult -> verificationResult.getPackageData().getPackageName().equals(packageName))
            .collect(Collectors.toSet())));

    return leads.map(leadMapper::toLeadListResponseDto);
  }

  private LeadListResponseDto setBiDirectionalRelationToLeadWithSaving(String packageName, LeadRequestDto leadDto) {
    var packageByName = packageRepository.findByPackageName(packageName);
    if (packageByName.isPresent()) {
      log.debug("Setting packageData with {}", keyValue(PACKAGE_NAME, packageName));
      var leadToSave = leadMapper.toLeadEntity(leadDto);

      return saveBiDirectionalRelation(packageByName.get(), leadToSave);
    }
    log.error(NOT_FOUND_ERROR_KEY_VALUE, keyValue(PACKAGE_NAME, packageName));
    throw new ResourceNotFoundException(PACKAGE_NAME + packageName);
  }

  private LeadListResponseDto saveBiDirectionalRelation(PackageData packageData, Lead lead) {

    lead.addPackage(packageData);
    packageData.addLeads(lead);

    lead.getCompany().getAddresses()
        .forEach(address -> address.setCompany(lead.getCompany()));
    lead.getCompany().addLeads(lead);

    lead.getVerificationResults().forEach(verificationResult -> {
      verificationResult.setLead(lead);
      verificationResult.setPackageData(packageData);
    });

    return leadMapper.toLeadListResponseDto(leadRepository.save(lead));
  }

  private VerificationResult updateVerificationResult(
      Set<VerificationResult> verificationResultFromRepository,
      String packageName,
      String updatedVerificationResult) {

    var verificationResultByEmailAndPackageName = verificationResultFromRepository
        .stream()
        .filter(verificationResult -> verificationResult.getPackageData().getPackageName().equals(packageName))
        .findFirst();

    if (verificationResultByEmailAndPackageName.isPresent()) {
      verificationResultByEmailAndPackageName.ifPresent(verificationResult ->
          verificationResult.setResult(updatedVerificationResult));
      log.debug("Update to {}", keyValue(VERIFICATION_RESULT, updatedVerificationResult));
      return verificationResultByEmailAndPackageName.get();
    }
    log.error(NOT_FOUND_ERROR_KEY_VALUE, keyValue(VERIFICATION_RESULT, verificationResultFromRepository));
    throw new ResourceNotFoundException(VERIFICATION_RESULT + verificationResultFromRepository);
  }

  private Lead findLeadByEmailAndPackageName(String email, String packageName) {
    return leadRepository.findByEmailAndPackageDataPackageName(email, packageName)
        .orElseThrow(() -> {
          log.error(NOT_FOUND_ERROR_KEY_VALUE, keyValue(EMAIL, email));
          throw new ResourceNotFoundException(EMAIL + email);
        });
  }
}
