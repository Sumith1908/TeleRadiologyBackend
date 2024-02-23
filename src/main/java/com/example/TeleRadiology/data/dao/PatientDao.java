package com.example.TeleRadiology.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.PatientEntity;

public interface PatientDao extends JpaRepository<PatientEntity, Integer> {
}
