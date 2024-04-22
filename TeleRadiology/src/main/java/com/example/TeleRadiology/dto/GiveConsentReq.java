package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class GiveConsentReq {
    private int doctorId;
    private int patientId;
    private int radiologistId;
    private int reportId;
    private int otp;
}
