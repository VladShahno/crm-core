package com.crm.verification.core.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.crm.verification.core.entity.PackageData;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PackageRepository extends PagingAndSortingRepository<PackageData, String> {

  boolean existsByPackageName(String packageName);

  Set<PackageData> findAllByPackageNameIn(Set<String> packageName);

  Optional<PackageData> findByPackageName(String packageName);

  List<PackageData> findAllByPackageNameOrderByPackageNameAsc(String packageName);
}
