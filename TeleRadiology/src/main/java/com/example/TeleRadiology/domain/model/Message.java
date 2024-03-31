package com.example.TeleRadiology.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Message {

    private int chatId;

    private String message;

    private int sender;

    private LocalDateTime timeStamp;
}
