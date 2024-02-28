package com.example.TeleRadiology.data.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.PatientEntity;

public interface PatientDao extends JpaRepository<PatientEntity, Integer> {
    Optional<PatientEntity> findByUserIdId(int id);

    boolean existsByEmail(String email);

    PatientEntity findByEmail(String email);
}
