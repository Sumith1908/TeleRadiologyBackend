package com.example.TeleRadiology.data.dao;

import java.util.List;
import java.util.Optional;

import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.NotificationEntity;

public interface NotificationDao extends JpaRepository<NotificationEntity, Integer> {
    java.util.Optional<List<NotificationEntity>> findAllByReciverIdIdAndReportIdId(int credId, int reportId);
    boolean existsByReportIdId(int reportId);
    Optional<NotificationEntity> findByRadiologistIdIdAndReportIdId(int radiologistId, int reportId);

    Optional<NotificationEntity> findByPatientIdId(int patientId);
}