package com.example.TeleRadiology.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.DoctorEntity;

public interface DoctorDao extends JpaRepository<DoctorEntity, Integer> {

}