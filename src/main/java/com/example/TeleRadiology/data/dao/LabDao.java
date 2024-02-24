package com.example.TeleRadiology.data.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.LabEntity;

public interface LabDao extends JpaRepository<LabEntity, Integer> {
    Optional<LabEntity> findByUserIdId(int id);
}
