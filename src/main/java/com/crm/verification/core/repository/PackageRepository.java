package com.crm.verification.core.repository;

import java.util.Set;

import com.crm.verification.core.model.PackageData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PackageRepository extends PagingAndSortingRepository<PackageData, Long> {

  boolean existsByPackageId(String packageId);

  boolean existsByPackageName(String packageName);

  Set<PackageData> findAllByPackageIdIn(Set<String> packageIds);

  Page<PackageData> findAllByPackageId(String packageId, Pageable pageable);
}
