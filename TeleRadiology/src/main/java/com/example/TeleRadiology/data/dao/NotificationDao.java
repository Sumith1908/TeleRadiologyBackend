package com.example.TeleRadiology.data.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.NotificationEntity;

public interface NotificationDao extends JpaRepository<NotificationEntity, Integer> {
 java.util.Optional<List<NotificationEntity>> findAllByReciverIdId(int id);
}
