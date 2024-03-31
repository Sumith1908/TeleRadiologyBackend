package com.example.TeleRadiology.data.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.MessageEntity;

public interface MessageDao extends JpaRepository<MessageEntity, Integer> {
    Optional<List<MessageEntity>> findAllByChatIdId(int chatId);
}
