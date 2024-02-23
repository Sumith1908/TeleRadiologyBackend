package com.example.TeleRadiology.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TeleRadiology.data.entities.MessageEntity;

public interface MessageDao extends JpaRepository<MessageEntity, Integer> {

}
