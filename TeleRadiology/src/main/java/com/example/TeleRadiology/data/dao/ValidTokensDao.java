package com.example.TeleRadiology.data.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.ValidTokensEntity;

public interface ValidTokensDao extends JpaRepository<ValidTokensEntity, Integer> {
    Optional<ValidTokensEntity> findByToken(String token);

    Optional<ValidTokensEntity> deleteByToken(String token);
}
