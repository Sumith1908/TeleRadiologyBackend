package com.example.TeleRadiology.data.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.SaltEntity;

public interface SaltDao extends JpaRepository<SaltEntity, Integer> {
    Optional<SaltEntity> findByUserIdId(int id);
}
