package com.example.TeleRadiology.data.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.CredentialsEntity;

public interface CredentialsDao extends JpaRepository<CredentialsEntity, Integer> {
    Optional<CredentialsEntity> findByEmailAndRoleId(String email, int role);

    boolean existsByEmail(String email);

    Optional<CredentialsEntity> findByEmail(String email);

    Optional<CredentialsEntity> findById(int id);
}