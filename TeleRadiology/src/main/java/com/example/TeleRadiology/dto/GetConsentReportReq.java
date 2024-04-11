package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class GetConsentReportReq {
    int patientID;
    int doctorID;
}
