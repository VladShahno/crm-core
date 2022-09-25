package com.crm.verification.core.repository;

import com.crm.verification.core.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
