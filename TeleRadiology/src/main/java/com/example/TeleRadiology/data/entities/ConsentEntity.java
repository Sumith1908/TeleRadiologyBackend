package com.example.TeleRadiology.data.entities;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "consent")
public class ConsentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "report_id", nullable = false)
    private ReportEntity reportId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patientId;

    @ManyToOne
    @JoinColumn(name = "viewer_id", nullable = false)
    private CredentialsEntity viewerId;

    @Column(name = "consent_date", nullable = false)
    private LocalDate consentDate;
}
