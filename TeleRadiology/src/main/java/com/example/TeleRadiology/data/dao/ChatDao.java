package com.example.TeleRadiology.data.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.ChatEntity;

public interface ChatDao extends JpaRepository<ChatEntity, Integer> {
    Optional<ChatEntity> findByUser1IdIdAndUser2IdIdAndReportIdId(int user1, int user2, int report);
}
