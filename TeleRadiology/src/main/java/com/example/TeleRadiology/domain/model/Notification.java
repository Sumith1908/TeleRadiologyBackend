package com.example.TeleRadiology.domain.model;

import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.DoctorEntity;
import com.example.TeleRadiology.data.entities.RadiologistEntity;
import com.example.TeleRadiology.data.entities.ReportEntity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data

public class Notification {
     private int doctorId;
     private int radiologistId;
     private int reportId;
     private int id;

}
