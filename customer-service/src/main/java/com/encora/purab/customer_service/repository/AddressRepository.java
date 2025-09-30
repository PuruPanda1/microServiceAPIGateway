package com.encora.purab.customer_service.repository;

import com.encora.purab.customer_service.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
