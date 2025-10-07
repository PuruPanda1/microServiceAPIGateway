package com.encora.purab.customer_service.repository;

import com.encora.purab.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c.customerId FROM Customer c WHERE c.email=:email")
    Optional<Long> findCustomerIdByEmail(@Param("email") String email);
}
