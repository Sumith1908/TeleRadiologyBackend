package com.example.TeleRadiology.domain.repositories;

import java.util.List;

import com.example.TeleRadiology.domain.model.Consent;
import com.example.TeleRadiology.domain.model.Report;
import com.example.TeleRadiology.dto.UploadRequest;

public interface ReportRepository {
    public int uploadPatientReport(UploadRequest upreq);

    public int giveConsent(int doctorId, int reportId, int patientId);

    public List<Report> getReportsOfPatient(int id);

    public Consent checkConsent(int viewerId, int reportId);

    public String setOtp(int otp, int id);

    public List<Consent> getViewers(int id);
}
