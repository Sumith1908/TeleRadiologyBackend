package com.example.TeleRadiology.data.dao;

import java.util.Optional;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.ReportEntity;

public interface ReportDao extends JpaRepository<ReportEntity, Integer> {
    Optional<List<ReportEntity>> findAllByPatientIdId(int id);
}
