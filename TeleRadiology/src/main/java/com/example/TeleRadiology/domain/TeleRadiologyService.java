package com.example.TeleRadiology.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.TeleRadiology.domain.model.Consent;
import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.domain.model.Lab;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.model.Radiologist;
import com.example.TeleRadiology.domain.model.Report;
import com.example.TeleRadiology.dto.ConsentResult;
import com.example.TeleRadiology.dto.CredentialsRequest;
import com.example.TeleRadiology.dto.CredentialsResult;
import com.example.TeleRadiology.dto.DoctorResult;
import com.example.TeleRadiology.dto.GiveConsentReq;
import com.example.TeleRadiology.dto.GiveConsentResult;
import com.example.TeleRadiology.dto.LabResult;
import com.example.TeleRadiology.dto.PatientResult;
import com.example.TeleRadiology.dto.RadiologistResult;
import com.example.TeleRadiology.dto.ReportResult;
import com.example.TeleRadiology.dto.UploadRequest;
import com.example.TeleRadiology.dto.UploadResult;
import com.example.TeleRadiology.exception.WrongPasswordException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeleRadiologyService {
    private final TeleRadiologyRepository teleRep;

    public CredentialsResult checkCredentials(String email, int role, String password) {
        Credentials cred = teleRep.checkLoginCredentials(email, role);
        CredentialsResult credRes = new CredentialsResult();
        credRes.setUser(cred.getId());
        if (!cred.getPassword().equals(password)) {
            credRes.setPassword(0);
            throw new WrongPasswordException("wrong password");
        } else {
            credRes.setPassword(1);
        }
        return credRes;
    }

    public PatientResult getPatient(int id) {
        Patient pat = teleRep.getPatient(id);
        return mapToDtoPatient(pat);
    }

    public DoctorResult getDoctor(int id) {
        Doctor doc = teleRep.getDoctor(id);
        return mapToDtoDoctor(doc);
    }

    public RadiologistResult getRadiologist(int id) {
        Radiologist rad = teleRep.getRadiologist(id);
        return mapToDtoRadiologist(rad);
    }

    public LabResult getLab(int id) {
        Lab lab = teleRep.getLab(id);
        return mapToDtoLabResult(lab);
    }

    public List<ReportResult> getAllReportsOfPatient(int id) {
        List<Report> reports = teleRep.getReportsOfPatient(id);
        return mapAllTodtoReports(reports);
    }

    public ConsentResult checkConsent(int viewerId, int reportId) {
        Consent cons = teleRep.checkConsent(viewerId, reportId);
        return mapToDtoConsent(cons);
    }

    public CredentialsResult addPatient(CredentialsRequest cred) {
        CredentialsResult credRes = new CredentialsResult();
        credRes.setUser(teleRep.addPatient(cred));
        credRes.setPassword(1);
        return credRes;
    }

    public UploadResult uploadReport(UploadRequest upreq) {
        UploadResult upRes = new UploadResult();
        int reportId = teleRep.uploadPatientReport(upreq);
        upRes.setRid(reportId);
        return upRes;
    }

    public GiveConsentResult giveConsent(GiveConsentReq req) {
        GiveConsentResult res = new GiveConsentResult();
        int viewerId = teleRep.giveConsent(req.getDoctorId(), req.getReportId(), req.getPatientId());
        res.setSuccess(1);
        res.setViewerId(viewerId);
        return res;
    }

    private DoctorResult mapToDtoDoctor(Doctor doc) {
        DoctorResult docRes = new DoctorResult();
        docRes.setId(doc.getId());
        docRes.setUserId(doc.getUserId());
        docRes.setFirstName(doc.getFirstName());
        docRes.setMiddleName(doc.getMiddleName());
        docRes.setLastName(doc.getLastName());
        docRes.setGender(doc.getGender());
        docRes.setHospitalAddress(doc.getHospitalAddress());
        docRes.setHospitalCity(doc.getHospitalCity());
        docRes.setHospitalState(doc.getHospitalState());
        docRes.setHospitalPinCode(doc.getHospitalPinCode());
        docRes.setHospitalEmail(doc.getHospitalEmail());
        docRes.setHospitalPhoneNumber(doc.getHospitalPhoneNumber());
        docRes.setExperience(doc.getExperience());
        docRes.setRating(doc.getRating());
        docRes.setHighestEducation(doc.getHighestEducation());
        docRes.setType(doc.getType());
        docRes.setProfilePhoto(doc.getProfilePhoto());

        return docRes;
    }

    private PatientResult mapToDtoPatient(Patient pat) {
        PatientResult patRes = new PatientResult();

        patRes.setId(pat.getId());
        patRes.setUserId(pat.getUserId());
        patRes.setFirstName(pat.getFirstName());
        patRes.setMiddleName(pat.getMiddleName());
        patRes.setLastName(pat.getLastName());
        patRes.setDateOfBirth(pat.getDateOfBirth());
        patRes.setGender(pat.getGender());
        patRes.setAddress(pat.getAddress());
        patRes.setCity(pat.getCity());
        patRes.setState(pat.getState());
        patRes.setPinCode(pat.getPinCode());
        patRes.setEmail(pat.getEmail());
        patRes.setPhoneNumber(pat.getPhoneNumber());
        patRes.setEmergencyContact(pat.getEmergencyContact());
        patRes.setBloodGroup(pat.getBloodGroup());
        patRes.setHeight(pat.getHeight());
        patRes.setWeight(pat.getWeight());
        patRes.setProfilePhoto(pat.getProfilePhoto());
        return patRes;
    }

    private RadiologistResult mapToDtoRadiologist(Radiologist rad) {
        RadiologistResult radRes = new RadiologistResult();
        radRes.setId(rad.getId());
        radRes.setUserId(rad.getUserId());
        radRes.setFirstName(rad.getFirstName());
        radRes.setMiddleName(rad.getMiddleName());
        radRes.setLastName(rad.getLastName());
        radRes.setEmail(rad.getEmail());
        radRes.setPhoneNumber(rad.getPhoneNumber());
        radRes.setExperience(rad.getExperience());
        radRes.setHighestEducation(rad.getHighestEducation());
        radRes.setProfilePhoto(rad.getProfilePhoto());

        return radRes;
    }

    private LabResult mapToDtoLabResult(Lab lab) {
        LabResult labRes = new LabResult();
        labRes.setId(lab.getId());
        labRes.setName(lab.getName());
        labRes.setAddress(lab.getAddress());
        labRes.setCity(lab.getCity());
        labRes.setState(lab.getState());
        labRes.setPinCode(lab.getPinCode());
        labRes.setUserId(lab.getUserId());
        labRes.setRating(lab.getRating());
        labRes.setPhoneNumber(lab.getPhoneNumber());

        return labRes;
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

}
