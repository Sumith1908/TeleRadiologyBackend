package com.example.TeleRadiology.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class NotificationDto {
    String doctor;
    String radiologist;
    String report;
    LocalDate date;
    int doctorId;
    int radiologistId;
    int id;
}
