package com.crm.verification.core.repository;

import java.util.Optional;

import com.crm.verification.core.entity.Company;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompanyRepository extends PagingAndSortingRepository<Company, String> {

  boolean existsByName(String name);

  Optional<Company> findByName(String name);

  void deleteByName(String name);
}
