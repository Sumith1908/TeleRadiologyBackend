package com.example.TeleRadiology.data.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.ConsentEntity;

public interface ConsentDao extends JpaRepository<ConsentEntity, Integer> {
    Optional<ConsentEntity> findByViewerIdIdAndReportIdId(int viewerId, int reportId);
    Optional<List<ConsentEntity>> findAllByReportIdId(int reportId);
}