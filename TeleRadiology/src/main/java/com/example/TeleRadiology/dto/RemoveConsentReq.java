package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class RemoveConsentReq {

    private int reportId;
    private int doctorId;
    private int radiologistId;
    private int patientId;
    private int otp;

}