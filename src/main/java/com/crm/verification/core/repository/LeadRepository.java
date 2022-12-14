package com.crm.verification.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.crm.verification.core.entity.Lead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LeadRepository extends PagingAndSortingRepository<Lead, String> {

  Optional<Lead> findByEmailAndPackageDataPackageName(String email, String packageName);

  Optional<Lead> findByEmail(String email);

  boolean existsByEmail(String email);

  Page<Lead> findAllByPackageDataPackageName(String packageName, Pageable pageable);

  List<Lead> findAllByEmailIn(Set<String> leadEmails);

  void deleteByEmail(String email);
}
