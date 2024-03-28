package com.example.TeleRadiology.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "otp")
public class OtpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "otp")
    private int otp;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patientId;

    @Column(name = "time")
    private LocalDateTime time;
}
