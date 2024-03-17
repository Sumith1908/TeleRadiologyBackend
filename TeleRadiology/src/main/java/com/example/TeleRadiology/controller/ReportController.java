package com.example.TeleRadiology.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeleRadiology.domain.services.ReportService;
import com.example.TeleRadiology.dto.ConsentRequest;
import com.example.TeleRadiology.dto.ConsentResult;
import com.example.TeleRadiology.dto.DetailsRequest;
import com.example.TeleRadiology.dto.GiveConsentReq;
import com.example.TeleRadiology.dto.GiveConsentResult;
import com.example.TeleRadiology.dto.ReportList;
import com.example.TeleRadiology.dto.ReportResult;
import com.example.TeleRadiology.dto.UploadRequest;
import com.example.TeleRadiology.dto.UploadResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teleRadiology")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*localhost*")
public class ReportController {
    private final ReportService repService;

    @PostMapping("/uploadReport")
    public UploadResult uploadReport(@RequestBody UploadRequest upreq) {

        UploadResult upRes = repService.uploadReport(upreq);

        return upRes;
    }

    @PostMapping("/giveConsent")
    public GiveConsentResult giveConsent(@RequestBody GiveConsentReq consentReq) {
        return repService.giveConsent(consentReq);
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

}
