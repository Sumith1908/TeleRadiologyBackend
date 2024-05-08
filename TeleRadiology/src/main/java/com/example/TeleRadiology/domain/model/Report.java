package com.example.TeleRadiology.domain.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Report {

    private int id;

    private String reportType;

    private int patientId;

    private int labId;

    private LocalDate dateOfIssue;

    private String initialRemarks;

    private int notification=0;

    private int patientAge=0;

    private String patientBloodGroup="";
}