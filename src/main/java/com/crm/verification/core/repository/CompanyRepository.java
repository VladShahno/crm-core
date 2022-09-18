package com.crm.verification.core.repository;

import java.util.Optional;

import com.crm.verification.core.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {

  boolean existsByName(String name);

  Optional<Company> findByName(String name);

  void deleteByName(String name);
}
