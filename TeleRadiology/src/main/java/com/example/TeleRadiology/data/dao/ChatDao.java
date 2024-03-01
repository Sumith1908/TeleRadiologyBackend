package com.example.TeleRadiology.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.ChatEntity;

public interface ChatDao extends JpaRepository<ChatEntity, Integer> {

}
