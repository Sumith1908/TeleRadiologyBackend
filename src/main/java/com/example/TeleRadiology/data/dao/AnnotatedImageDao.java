package com.example.TeleRadiology.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.AnnotatedImageEntity;

public interface AnnotatedImageDao extends JpaRepository<AnnotatedImageEntity, Integer> {

}
