package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class AddChatReq {
    int reportId;
    int user1Id;
    int user2Id;
}
