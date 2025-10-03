package com.encora.purab.authentication_service.repository;

import com.encora.purab.authentication_service.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {
    @Query("SELECT u FROM UserCredential u WHERE u.username=:username")
    Optional<UserCredential> findByUserName(String username);
}
