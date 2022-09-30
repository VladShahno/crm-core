package com.crm.verification.core.service;

import static com.crm.verification.core.common.Constants.Logging.EMAIL;
import static com.crm.verification.core.common.Constants.Logging.LEAD;
import static com.crm.verification.core.common.Constants.Logging.LEAD_NOT_FOUND;
import static com.crm.verification.core.common.Constants.Logging.NAME;
import static com.crm.verification.core.common.Constants.Logging.NOT_FOUND_ERROR_KEY_VALUE;
import static com.crm.verification.core.common.Constants.Logging.VERIFICATION_RESULT;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.crm.verification.core.dto.request.create.LeadCreateRequestDto;
import com.crm.verification.core.dto.request.update.LeadUpdateRequestDto;
import com.crm.verification.core.dto.response.list.LeadListResponseDto;
import com.crm.verification.core.dto.response.profile.LeadProfileResponseDto;
import com.crm.verification.core.entity.Lead;
import com.crm.verification.core.entity.PackageData;
import com.crm.verification.core.entity.VerificationResult;
import com.crm.verification.core.exception.ResourceExistsException;
import com.crm.verification.core.exception.ResourceNotFoundException;
import com.crm.verification.core.mapper.LeadMapper;
import com.crm.verification.core.repository.LeadRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Setter
@Service
@Transactional
public class LeadService {

  private final LeadRepository leadRepository;
  private final PackageService packageService;
  private final CompanyService companyService;
  private final LeadMapper leadMapper;

  public LeadService(
      LeadRepository leadRepository,
      @Lazy PackageService packageService,
      @Lazy CompanyService companyService,
      LeadMapper leadMapper) {
    this.leadRepository = leadRepository;
    this.packageService = packageService;
    this.companyService = companyService;
    this.leadMapper = leadMapper;
  }

  @Transactional
  public LeadListResponseDto createLeadProfile(LeadCreateRequestDto leadDto, String packageName) {
    if (leadRepository.existsByEmail(leadDto.getEmail())) {
      log.error("Lead with {} already exists", keyValue(EMAIL, leadDto.getEmail()));
      throw new ResourceExistsException(EMAIL + leadDto.getEmail());
    }
    return saveLeadToPackage(packageName, leadDto);
  }

  public LeadProfileResponseDto updateLeadProfileByEmailAndPackageName(
      String packageName,
      String email,
      LeadUpdateRequestDto leadRequestDto) {

    var leadByEmail = findLeadByEmail(email);
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
      throw new ResourceNotFoundException(LEAD, EMAIL, email);
    });
  }

  public LeadProfileResponseDto changeLeadCompany(String newCompanyName, String leadEmail) {
    var leadToUpdate = findLeadByEmail(leadEmail);
    var newCompany = companyService.findCompanyByName(newCompanyName);

    companyService.removeLeadFromCompany(leadToUpdate, leadToUpdate.getCompany());

    leadToUpdate.setCompany(newCompany);
    newCompany.addLeads(leadToUpdate);

    log.debug("Saving lead with {} with new {}", keyValue(EMAIL, leadEmail), keyValue(NAME, newCompanyName));
    return leadMapper.toLeadProfileResponseDto(leadRepository.save(leadToUpdate));
  }

  public LeadProfileResponseDto getLeadProfileByEmail(String email) {
    log.debug("Getting lead with {}", keyValue(EMAIL, email));
    return leadRepository.findById(email).map(leadMapper::toLeadProfileResponseDto)
        .orElseThrow(() -> {
          log.error(LEAD_NOT_FOUND, keyValue(EMAIL, email));
          return new ResourceNotFoundException(LEAD, EMAIL, email);
        });
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

  private LeadListResponseDto saveLeadToPackage(String packageName, LeadCreateRequestDto leadDto) {
    var packageByName = packageService.getPackageByPackageName(packageName);
    var leadToSave = leadMapper.toLeadEntity(leadDto);

    setBiDirectionalRelation(packageByName, leadToSave, leadDto.getVerificationResults());

    return leadMapper.toLeadListResponseDto(leadRepository.save(leadToSave));
  }

  private void setBiDirectionalRelation(PackageData packageData, Lead lead, String result) {

    lead.addPackage(packageData);
    packageData.addLeads(lead);

    lead.getCompany().getAddresses()
        .forEach(address -> address.setCompany(lead.getCompany()));
    lead.getCompany().addLeads(lead);

    lead.addVerificationResult(new VerificationResult());

    lead.getVerificationResults().forEach(verificationResult -> {
      verificationResult.setLead(lead);
      verificationResult.setPackageData(packageData);
      verificationResult.setResult(result);
    });
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
    throw new ResourceNotFoundException(VERIFICATION_RESULT);
  }

  private Lead findLeadByEmail(String email) {
    return leadRepository.findByEmail(email)
        .orElseThrow(() -> {
          log.error(NOT_FOUND_ERROR_KEY_VALUE, keyValue(EMAIL, email));
          throw new ResourceNotFoundException(LEAD, EMAIL, email);
        });
  }

  public List<Lead> findAllByEmailIn(Set<String> leadEmails) {
    log.debug("Getting all leads by {}", keyValue(EMAIL, leadEmails));
    return leadRepository.findAllByEmailIn(leadEmails);
  }

  public void bulkLeadSave(List<Lead> leads) {
    leadRepository.saveAll(leads);
  }
}
