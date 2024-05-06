package com.example.TeleRadiology.data.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.AnnotatedImageEntity;

public interface AnnotatedImageDao extends JpaRepository<AnnotatedImageEntity, Integer> {
    Optional<List<AnnotatedImageEntity>> findAllByChatIdId(int id);
}
