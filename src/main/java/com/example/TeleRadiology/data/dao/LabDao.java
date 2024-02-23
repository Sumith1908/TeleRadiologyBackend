package com.example.TeleRadiology.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.LabEntity;

public interface LabDao extends JpaRepository<LabEntity, Integer> {

}
