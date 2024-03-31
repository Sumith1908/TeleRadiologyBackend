package com.example.TeleRadiology.dto;

import java.util.List;

import com.example.TeleRadiology.domain.model.Message;

import lombok.Data;

@Data
public class MessagesResult {
    List<Message> messages;
}
