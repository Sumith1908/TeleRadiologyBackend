package com.example.TeleRadiology.data.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.OtpEntity;

public interface OtpDao extends JpaRepository<OtpEntity, Integer> {
    Optional<OtpEntity> findByCredIdId(int id);

}
