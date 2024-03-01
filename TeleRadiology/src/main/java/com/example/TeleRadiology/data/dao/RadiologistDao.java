package com.example.TeleRadiology.data.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.RadiologistEntity;

public interface RadiologistDao extends JpaRepository<RadiologistEntity, Integer> {
    Optional<RadiologistEntity> findByUserIdId(int id);
}
