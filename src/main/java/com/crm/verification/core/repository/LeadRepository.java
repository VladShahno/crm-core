package com.crm.verification.core.repository;

import java.util.Optional;

import com.crm.verification.core.model.Lead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LeadRepository extends PagingAndSortingRepository<Lead, String> {

  Optional<Lead> findByEmail(String email);

  boolean existsByEmail(String email);

  Page<Lead> findAllByPackageDataPackageId(String packageId, Pageable pageable);

}
