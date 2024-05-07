package com.example.TeleRadiology.data.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.DoctorEntity;

public interface DoctorDao extends JpaRepository<DoctorEntity, Integer> {
    Optional<DoctorEntity> findByUserIdId(int id);
    Optional<DoctorEntity> findById(int id);
}