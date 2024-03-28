package com.example.TeleRadiology.domain.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.TeleRadiology.data.dao.OtpDao;
import com.example.TeleRadiology.data.entities.OtpEntity;
import com.example.TeleRadiology.domain.model.Consent;
import com.example.TeleRadiology.domain.model.Report;
import com.example.TeleRadiology.domain.repositories.ReportRepository;
import com.example.TeleRadiology.dto.ConsentResult;
import com.example.TeleRadiology.dto.GiveConsentReq;
import com.example.TeleRadiology.dto.GiveConsentResult;
import com.example.TeleRadiology.dto.ReportResult;
import com.example.TeleRadiology.dto.UploadRequest;
import com.example.TeleRadiology.dto.UploadResult;
import com.example.TeleRadiology.exception.NoOtpException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepo;
    private final OtpDao otpDao;

    public List<ReportResult> getAllReportsOfPatient(int id) {
        List<Report> reports = reportRepo.getReportsOfPatient(id);
        return mapAllTodtoReports(reports);
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

    public GiveConsentResult giveConsent(GiveConsentReq req) {
        OtpEntity otp = otpDao.findByPatientIdId(req.getPatientId()).orElseThrow(
                () -> new NoOtpException("Wrong Otp"));
        if (otp.getOtp() != req.getOtp()) {
            throw new NoOtpException("Wrong Otp");
        }
        LocalDateTime time = otp.getTime();
        LocalDateTime cur = LocalDateTime.now();
        long minutesDifference = ChronoUnit.MINUTES.between(time, cur);

        if (minutesDifference > 15) {
            throw new NoOtpException("Otp Expired");
        }
        GiveConsentResult res = new GiveConsentResult();
        int viewerId = reportRepo.giveConsent(req.getDoctorId(), req.getReportId(), req.getPatientId());
        res.setSuccess(1);
        res.setViewerId(viewerId);
        return res;
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
