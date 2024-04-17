package com.example.TeleRadiology.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.TeleRadiology.domain.model.Patient;
import org.springframework.web.bind.annotation.*;

import com.example.TeleRadiology.domain.services.EmailOtpService;
import com.example.TeleRadiology.domain.services.ReportService;
import com.example.TeleRadiology.domain.model.Consent;
import com.example.TeleRadiology.domain.model.Report;
import com.example.TeleRadiology.dto.ConsentRequest;
import com.example.TeleRadiology.dto.ConsentResult;
import com.example.TeleRadiology.dto.GetConsentReportReq;
import com.example.TeleRadiology.dto.DetailsRequest;
import com.example.TeleRadiology.dto.GiveConsentReq;
import com.example.TeleRadiology.dto.GiveConsentResult;
import com.example.TeleRadiology.dto.ReportList;
import com.example.TeleRadiology.dto.ReportResult;
import com.example.TeleRadiology.dto.UploadRequest;
import com.example.TeleRadiology.dto.UploadResult;
import com.example.TeleRadiology.dto.RemoveConsentReq;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teleRadiology")
// @CrossOrigin(originPatterns = "*localhost*")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService repService;
    private final EmailOtpService email;

    @PostMapping("/uploadReport")
    public UploadResult uploadReport(@RequestBody UploadRequest upreq) {

        UploadResult upRes = repService.uploadReport(upreq);

        return upRes;
    }

    @PostMapping("/giveConsent")
    public GiveConsentResult giveConsent(@RequestBody GiveConsentReq consentReq) {
        return repService.giveConsent(consentReq);
    }

    @PostMapping("/otpVerification/{id}")
    public void verifyOtp(@PathVariable("id") int id) {
        int otp = (int) (Math.random() * 900000) + 100000;
        email.sendEmailOtp("OTP verification", otp, id);
    }

    @PostMapping("/getPatientReports")
    public ReportList getReportsOfPatients(@RequestBody DetailsRequest detReq) {
        List<ReportResult> reports = repService.getAllReportsOfPatient(detReq.getId());
        ReportList repList = new ReportList();
        repList.setReports(reports);
        return repList;
    }

    @PostMapping("/checkConsent")
    public ConsentResult checkConsent(@RequestBody ConsentRequest consReq) {
        ConsentResult consRes = repService.checkConsent(consReq.getViewerId(), consReq.getReportId());
        return consRes;
    }

    @GetMapping("/getReportViewers/{id}")
    public List<Consent> getViewers(@PathVariable("id") int id) {
        List<Consent> ConsentList = new ArrayList<>();
        ConsentList = repService.getReportViewers(id);

        return ConsentList;
    }

    @PostMapping("/removeConsent")
    public int removeConsent(@RequestBody RemoveConsentReq removeConsentReq) {
        repService.deleteConsent(removeConsentReq);

        return 0;
    }

    @GetMapping("/getConsentPatients/{id}")
    public List<Patient> getConsentPatients(@PathVariable("id") int id) {
        // int viewerId=viewId.intValue();
        List<Patient> conPat = new ArrayList<>();
        conPat = repService.getConsentPat(id);
        return conPat;
    }

    @PostMapping("/getConsentReports")
    public List<Report> getConsentReports(@RequestBody GetConsentReportReq getConsentReportReq) {
        List<Report> consentedReports = new ArrayList<>();
        consentedReports = repService.getReports(getConsentReportReq);
        return consentedReports;
    }
}