package com.example.TeleRadiology.domain.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Consent {

    private int id;

    private int reportId;

    private int viewerId;

    private int patientId;

    private LocalDate consentDate;
}
