package com.example.TeleRadiology.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ReportResult {
    private int id;

    private String report = "";

    private String reportType;

    private int patientId;

    private int labId;

    private LocalDate dateOfIssue;

    private String initialRemarks;

    private int notification;
}