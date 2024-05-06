package com.example.TeleRadiology.domain.repositories;

import java.util.List;

import com.example.TeleRadiology.domain.model.AnnotatedImage;
import com.example.TeleRadiology.domain.model.Consent;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.model.Report;
import com.example.TeleRadiology.dto.GetConsentReportReq;
import com.example.TeleRadiology.dto.UploadRequest;
import com.example.TeleRadiology.dto.RemoveConsentReq;

public interface ReportRepository {
    public int uploadPatientReport(UploadRequest upreq);

    public int giveConsent(int doctorId, int reportId, int patientId, int radioId);

    public List<Report> getReportsOfPatient(int id);

    public Consent checkConsent(int viewerId, int reportId);

    public String setOtp(int otp, int id);

    public List<Consent> getViewers(int id);

    public int removeConsent(RemoveConsentReq removeConsentReq);

    public List<Patient> getConsentPatients(int viewId);

    public List<Report> getConsentedReports(GetConsentReportReq getConsentReportReq);

    public int uploadAnnotation(int chatId);

    public List<AnnotatedImage> getAllAnnotations(int chatId);
}