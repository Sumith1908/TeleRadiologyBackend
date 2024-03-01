package com.example.TeleRadiology.domain;

import java.util.List;

import com.example.TeleRadiology.domain.model.Consent;
import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.domain.model.Lab;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.model.Radiologist;
import com.example.TeleRadiology.domain.model.Report;
import com.example.TeleRadiology.dto.CredentialsRequest;
import com.example.TeleRadiology.dto.UploadRequest;

public interface TeleRadiologyRepository {
    public Credentials checkLoginCredentials(String email, int role);

    public Patient getPatient(int id);

    public Doctor getDoctor(int id);

    public Radiologist getRadiologist(int id);

    public Lab getLab(int id);

    public List<Report> getReportsOfPatient(int id);

    public Consent checkConsent(int viewerId, int reportId);

    public int addPatient(CredentialsRequest cred);

    public int uploadPatientReport(UploadRequest upreq);

    public int giveConsent(int doctorId, int reportId, int patientId);

}
