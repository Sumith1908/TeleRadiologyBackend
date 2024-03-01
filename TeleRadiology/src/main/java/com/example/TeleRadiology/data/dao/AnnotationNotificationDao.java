package com.example.TeleRadiology.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.AnnotationNotificationEntity;

public interface AnnotationNotificationDao extends JpaRepository<AnnotationNotificationEntity, Integer> {

}
