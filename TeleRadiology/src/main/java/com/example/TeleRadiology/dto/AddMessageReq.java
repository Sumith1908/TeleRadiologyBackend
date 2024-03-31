package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class AddMessageReq {
    int sender;
    int reciever;
    int report;
    String message;
}
