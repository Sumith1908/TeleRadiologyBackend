package com.example.TeleRadiology.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Otp {
    private int id;

    private int otp;

    private int patientId;

    private LocalDateTime time;
}
