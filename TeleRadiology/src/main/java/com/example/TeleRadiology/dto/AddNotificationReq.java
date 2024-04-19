package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class AddNotificationReq {

    int patientId;
    int doctorId;
    int reciverId;
    int radiologistId;
    int reportId;
}
