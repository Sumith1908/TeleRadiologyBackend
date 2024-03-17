package com.example.TeleRadiology.data.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.RoleEntity;

public interface CredentialsDao extends JpaRepository<CredentialsEntity, Integer> {
    Optional<CredentialsEntity> findByEmailAndRole(String email, RoleEntity role);

    boolean existsByEmail(String email);

    Optional<CredentialsEntity> findByEmail(String email);
}
