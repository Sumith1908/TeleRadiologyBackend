package com.example.TeleRadiology.domain.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.TeleRadiology.data.dao.OtpDao;
import com.example.TeleRadiology.data.dao.PatientDao;
import com.example.TeleRadiology.data.entities.OtpEntity;
import com.example.TeleRadiology.data.entities.PatientEntity;
import com.example.TeleRadiology.domain.model.Consent;
import com.example.TeleRadiology.domain.model.Report;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.repositories.ReportRepository;
import com.example.TeleRadiology.dto.ConsentResult;
import com.example.TeleRadiology.dto.GetAllReportsReq;
import com.example.TeleRadiology.dto.GetAllReportsRes;
import com.example.TeleRadiology.dto.GiveConsentReq;
import com.example.TeleRadiology.dto.GetConsentReportReq;
import com.example.TeleRadiology.dto.GiveConsentResult;
import com.example.TeleRadiology.dto.ReportResult;
import com.example.TeleRadiology.dto.UploadRequest;
import com.example.TeleRadiology.dto.UploadResult;
import com.example.TeleRadiology.dto.RemoveConsentReq;
import com.example.TeleRadiology.dto.ReportDTO;
import com.example.TeleRadiology.exception.NoOtpException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepo;
    private final OtpDao otpDao;
    private final ChatService chatService;
    private final PatientDao patientDao;
    private final ImageService imgService;

    public List<ReportResult> getAllReportsOfPatient(int id) {
        List<Report> reports = reportRepo.getReportsOfPatient(id);
        List<ReportResult> reps = mapAllTodtoReports(reports);
        // image req commented untill req
        // List<Integer> ids = reps.stream().map(x ->
        // x.getId()).collect(Collectors.toList());
        // GetAllReportsReq reqBody = new GetAllReportsReq();
        // reqBody.setReportIds(ids);
        // GetAllReportsRes reportImages = (GetAllReportsRes)
        // imgService.callImageServerPost("/getAllReports", reqBody,
        // GetAllReportsRes.class);
        // Collections.sort(reps, Comparator.comparing(ReportResult::getId));
        // Collections.sort(reportImages.getReports(),
        // Comparator.comparing(ReportDTO::getReportId));
        // for (int i = 0; i < reps.size(); i++) {
        // reps.get(i).setReport(reportImages.getReports().get(i).getReport());
        // }
        return reps;
    }

    public ConsentResult checkConsent(int viewerId, int reportId) {
        Consent cons = reportRepo.checkConsent(viewerId, reportId);
        return mapToDtoConsent(cons);
    }

    public UploadResult uploadReport(UploadRequest upreq) {
        UploadResult upRes = new UploadResult();
        int reportId = reportRepo.uploadPatientReport(upreq);
        upRes.setRid(reportId);
        return upRes;
    }

    public List<Consent> getReportViewers(int reportId) {
        List<Consent> ConsentList = new ArrayList<>();
        ConsentList = reportRepo.getViewers(reportId);
        return ConsentList;
    }

    public GiveConsentResult giveConsent(GiveConsentReq req) {

        GiveConsentResult res = new GiveConsentResult();

        if (verifyOTP(req.getPatientId(), req.getOtp())) {
            int viewerId = reportRepo.giveConsent(req.getDoctorId(), req.getReportId(), req.getPatientId(),
                    req.getRadiologistId());
            res.setSuccess(1);
            res.setViewerId(viewerId);
            PatientEntity pat = patientDao.findById(req.getPatientId()).orElse(null);
            if (pat != null) {
                chatService.addChat(viewerId, pat.getUserId().getId(), req.getReportId());
            }
        }
        return res;
    }

    public int deleteConsent(RemoveConsentReq removeConsentReq) {

        if (verifyOTP(removeConsentReq.getPatientId(), removeConsentReq.getOtp())) {
            reportRepo.removeConsent(removeConsentReq);
        }

        return 0;
    }

    private boolean verifyOTP(int patientId, int sentOtp) {
        OtpEntity otp = otpDao.findByPatientIdId(patientId).orElseThrow(
                () -> new NoOtpException("Wrong Otp"));
        if (otp.getOtp() != sentOtp) {
            throw new NoOtpException("Wrong Otp");
        }
        LocalDateTime time = otp.getTime();
        LocalDateTime cur = LocalDateTime.now();
        long minutesDifference = ChronoUnit.MINUTES.between(time, cur);

        if (minutesDifference > 15) {
            throw new NoOtpException("Otp Expired");
        }

        return true;
    }

    public List<Patient> getConsentPat(int viewId) {
        List<Patient> conPat = reportRepo.getConsentPatients(viewId);
        return conPat;
    }

    public List<Report> getReports(GetConsentReportReq getConsentReportReq) {
        List<Report> consentedReports = new ArrayList<Report>();
        consentedReports = reportRepo.getConsentedReports(getConsentReportReq);
        return consentedReports;
    }

    private ConsentResult mapToDtoConsent(Consent cons) {
        ConsentResult consRes = new ConsentResult();
        LocalDate currentDate = LocalDate.now();
        LocalDate consenDate = cons.getConsentDate();
        long daysBetween = ChronoUnit.DAYS.between(consenDate, currentDate);
        if (daysBetween <= 90) {
            consRes.setConsent(1);
        }
        return consRes;
    }

    public List<ReportResult> mapAllTodtoReports(List<Report> reports) {
        List<ReportResult> reportsRes = new ArrayList<>();

        for (Report report : reports) {
            reportsRes.add(mapToDtoReportResult(report));
        }

        return reportsRes;
    }

    private ReportResult mapToDtoReportResult(Report report) {
        ReportResult reportResult = new ReportResult();
        reportResult.setId(report.getId());
        reportResult.setReportType(report.getReportType());
        reportResult.setPatientId(report.getPatientId());
        reportResult.setLabId(report.getLabId());
        reportResult.setDateOfIssue(report.getDateOfIssue());
        reportResult.setInitialRemarks(report.getInitialRemarks());

        return reportResult;
    }
}