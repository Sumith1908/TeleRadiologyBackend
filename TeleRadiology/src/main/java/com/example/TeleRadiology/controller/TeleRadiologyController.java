package com.example.TeleRadiology.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.TeleRadiology.domain.TeleRadiologyService;
import com.example.TeleRadiology.dto.ConsentRequest;
import com.example.TeleRadiology.dto.ConsentResult;
import com.example.TeleRadiology.dto.CredentialsRequest;
import com.example.TeleRadiology.dto.CredentialsResult;
import com.example.TeleRadiology.dto.DetailsRequest;
import com.example.TeleRadiology.dto.DoctorResult;
import com.example.TeleRadiology.dto.GiveConsentReq;
import com.example.TeleRadiology.dto.GiveConsentResult;
import com.example.TeleRadiology.dto.LabResult;
import com.example.TeleRadiology.dto.PatientResult;
import com.example.TeleRadiology.dto.RadiologistResult;
import com.example.TeleRadiology.dto.ReportResult;
import com.example.TeleRadiology.dto.UploadRequest;
import com.example.TeleRadiology.dto.UploadResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teleRadiology")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*localhost*")
public class TeleRadiologyController {
    private final TeleRadiologyService teleRadService;

    @PostMapping("/loginCredentials")
    public CredentialsResult checkCredentials(@RequestBody CredentialsRequest credReq) {
        CredentialsResult credRes = teleRadService.checkCredentials(credReq.getEmail(), credReq.getRole(),
                credReq.getPassword());
        return credRes;
    }

    @PostMapping("/uploadImage")
    public void upload(@RequestParam("file") MultipartFile file) {

    }

    @PostMapping("/getPatient")
    public PatientResult getPatient(@RequestBody DetailsRequest detReq) {
        PatientResult pat = teleRadService.getPatient(detReq.getId());
        return pat;
    }

    @PostMapping("/getDoctor")
    public DoctorResult getDoctor(@RequestBody DetailsRequest detReq) {
        DoctorResult doc = teleRadService.getDoctor(detReq.getId());
        return doc;
    }

    @PostMapping("/getRadiologist")
    public RadiologistResult getRadiologist(@RequestBody DetailsRequest detReq) {
        RadiologistResult rad = teleRadService.getRadiologist(detReq.getId());
        return rad;
    }

    @PostMapping("/getLab")
    public LabResult getLab(@RequestBody DetailsRequest detReq) {
        LabResult lab = teleRadService.getLab(detReq.getId());
        return lab;
    }

    @PostMapping("/getPatientReports")
    public List<ReportResult> getReportsOfPatients(@RequestBody DetailsRequest detReq) {
        List<ReportResult> reports = teleRadService.getAllReportsOfPatient(detReq.getId());
        return reports;
    }

    @PostMapping("/checkConsent")
    public ConsentResult checkConsent(@RequestBody ConsentRequest consReq) {
        ConsentResult consRes = teleRadService.checkConsent(consReq.getViewerId(), consReq.getReportId());
        return consRes;
    }

    @PostMapping("/createPatientCred")
    public CredentialsResult createPatientCred(@RequestBody CredentialsRequest cred) {
        CredentialsResult credRes = teleRadService.addPatient(cred);
        return credRes;
    }

    @PostMapping("/uploadReport")
    public UploadResult uploadReport(@RequestBody UploadRequest upreq) {

        UploadResult upRes = teleRadService.uploadReport(upreq);

        return upRes;
    }

    @PostMapping("/giveConsent")
    public GiveConsentResult giveConsent(@RequestBody GiveConsentReq consentReq) {
        return teleRadService.giveConsent(consentReq);
    }
}
