package com.example.TeleRadiology.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.ReportEntity;

public interface ReportDao extends JpaRepository<ReportEntity, Integer> {

}
