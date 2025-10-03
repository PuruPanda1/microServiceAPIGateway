package com.encora.purab.authentication_service.repository;

import com.encora.purab.authentication_service.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {
}
