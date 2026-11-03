package com.example.application.repository;

import com.example.application.entity.CredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<CredentialEntity, Long> {

    Optional<CredentialEntity> findByUserUsernameAndDeletedFalse(String username);
}
