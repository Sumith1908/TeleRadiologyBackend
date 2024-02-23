package com.example.TeleRadiology.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "report")
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "report_type", nullable = false)
    private String reportType;

    @ManyToOne
    @JoinColumn(name = "patient")
    private PatientEntity patientId;

    @Column(name = "date_of_issue")
    private LocalDate dateOfIssue;

    @Column(name = "initial_remarks")
    private String initialRemarks;

    @ManyToOne
    @JoinColumn(name = "lab_id")
    private LabEntity labId;
}
