package com.example.TeleRadiology.data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "notifications")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patientId;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctorId;

    @ManyToOne
    @JoinColumn(name = "reciver_id")
    private CredentialsEntity reciverId;

    @ManyToOne
    @JoinColumn(name = "radiologist_id")
    private RadiologistEntity radiologistId;

    @ManyToOne
    @JoinColumn(name = "report_id")
    private ReportEntity reportId;


}
