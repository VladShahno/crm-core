package com.crm.verification.core.repository;

import java.util.Optional;

import com.crm.verification.core.model.Lead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface LeadRepository extends PagingAndSortingRepository<Lead, Long> {

  Optional<Lead> findByEmail(String email);

  boolean existsByEmail(String email);

  @Query("select p from PackageData p where p.packageId = :packageId")
  Page<Lead> findAllByPackageDataPackageId(@Param("packageId") String packageId, Pageable pageable);
}
