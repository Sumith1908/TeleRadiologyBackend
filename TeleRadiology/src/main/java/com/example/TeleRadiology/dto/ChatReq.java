package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class ChatReq {
    private int user1Id;
    private int user2Id;
    private int reportId;
}
