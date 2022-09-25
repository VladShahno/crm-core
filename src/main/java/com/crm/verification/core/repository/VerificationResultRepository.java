package com.crm.verification.core.repository;

import com.crm.verification.core.entity.VerificationResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationResultRepository extends JpaRepository<VerificationResult, Long> {
}
