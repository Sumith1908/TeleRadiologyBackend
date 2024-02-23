package com.example.TeleRadiology.domain.model;

import lombok.Data;

@Data
public class Chat {
    private int id;

    private int user1Id;

    private int user2Id;

    private int reportId;
}
