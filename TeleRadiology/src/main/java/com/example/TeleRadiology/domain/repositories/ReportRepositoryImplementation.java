package com.example.TeleRadiology.domain.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.TeleRadiology.data.dao.AnnotatedImageDao;
import com.example.TeleRadiology.data.dao.ChatDao;
import com.example.TeleRadiology.data.dao.ConsentDao;
import com.example.TeleRadiology.data.dao.CredentialsDao;
import com.example.TeleRadiology.data.dao.DoctorDao;
import com.example.TeleRadiology.data.dao.LabDao;
import com.example.TeleRadiology.data.dao.OtpDao;
import com.example.TeleRadiology.data.dao.PatientDao;
import com.example.TeleRadiology.data.dao.RadiologistDao;
import com.example.TeleRadiology.data.dao.ReportDao;
import com.example.TeleRadiology.data.entities.AnnotatedImageEntity;
import com.example.TeleRadiology.data.entities.ChatEntity;
import com.example.TeleRadiology.data.entities.ConsentEntity;
import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.DoctorEntity;
import com.example.TeleRadiology.data.entities.LabEntity;
import com.example.TeleRadiology.data.entities.OtpEntity;
import com.example.TeleRadiology.data.entities.PatientEntity;
import com.example.TeleRadiology.data.entities.RadiologistEntity;
import com.example.TeleRadiology.data.entities.ReportEntity;
import com.example.TeleRadiology.domain.model.Consent;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.model.Report;
import com.example.TeleRadiology.dto.GetConsentReportReq;
import com.example.TeleRadiology.dto.RemoveConsentReq;
import com.example.TeleRadiology.dto.UploadRequest;
import com.example.TeleRadiology.exception.ConsentNotFoundException;
import com.example.TeleRadiology.exception.DoctoNotFoundException;
import com.example.TeleRadiology.exception.GlobalException;
import com.example.TeleRadiology.exception.LabNotFoundException;
import com.example.TeleRadiology.exception.PatientNotFoundException;
import com.example.TeleRadiology.exception.ReportsNotFoundException;
import com.example.TeleRadiology.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReportRepositoryImplementation implements ReportRepository {

    private final ReportDao repDao;
    private final ConsentDao consentDao;
    private final PatientDao patDao;
    private final DoctorDao docDao;
    private final LabDao labDao;
    private final OtpDao otpDao;
    private final RadiologistDao radDao;
    private final CredentialsDao credDao;
    private final AnnotatedImageDao annotationDao;
    private final ChatDao chatDao;

    public List<Report> getReportsOfPatient(int id) {
        List<ReportEntity> reportList = repDao.findAllByPatientIdId(id).orElseThrow(
                () -> new ReportsNotFoundException("No reports for given patient id"));
        return mapAllToDomainReportEntity(reportList);
    }

    public Consent checkConsent(int viewerId, int reportId) {
        ConsentEntity consEnt = consentDao.findByViewerIdIdAndReportIdId(viewerId, reportId).orElseThrow(
                () -> new ConsentNotFoundException("You do not have consent for this report"));
        return mapToDomainConsentEntity(consEnt);
    }

    public int uploadPatientReport(UploadRequest upreq) {

        ReportEntity repEnt = null;
        if (!patDao.existsByEmail(upreq.getPatEmail()))
            throw new PatientNotFoundException("No such patient");
        else
            repEnt = repDao.save(mapToRepEntity(upreq));

        if (repEnt != null) {
            return repEnt.getId();
        } else {
            throw new GlobalException("Unable to save");
        }
    }

    public List<Consent> getViewers(int id) {
        List<ConsentEntity> ConsentList = new ArrayList<>();
        ConsentList = consentDao.findAllByReportIdId(id).orElseThrow(
                () -> new GlobalException("Viewers Not Found"));
        return mapAllToDomainConsentEntity(ConsentList);
    }

    @Transactional
    public int removeConsent(RemoveConsentReq removeConsentReq) {
        DoctorEntity doc = docDao.findById(removeConsentReq.getDoctorId()).orElseThrow(
                () -> new DoctoNotFoundException("No such doctor"));
        consentDao.deleteByReportIdIdAndViewerIdId(removeConsentReq.getReportId(), doc.getUserId().getId());
        return 0;
    }

    @Override
    public String setOtp(int otp, int id) {
        PatientEntity pat = patDao.findById(id).orElse(null);
        OtpEntity otpEnt = otpDao.findByPatientIdId(id).orElse(null);
        LocalDateTime time = LocalDateTime.now();
        if (otpEnt == null) {
            OtpEntity newOtpEntity = new OtpEntity();
            newOtpEntity.setOtp(otp);
            newOtpEntity.setPatientId(pat);
            newOtpEntity.setTime(time);
            otpDao.save(newOtpEntity);
        } else {
            otpEnt.setOtp(otp);
            otpEnt.setTime(time);
            otpDao.save(otpEnt);
        }
        return pat.getEmail();
    }

    public List<Patient> getConsentPatients(int viewerId) {
        List<ConsentEntity> conEnt = new ArrayList<>();
        HashSet<Patient> patEnt = new HashSet<>();
        List<Patient> patients = new ArrayList<>();

        conEnt = consentDao.findAllByViewerIdId(viewerId).orElseThrow(
                () -> new GlobalException("Reports Not Found"));

        for (int i = 0; i < conEnt.size(); i++) {
            patEnt.add(mapToDomainPatientEntity(conEnt.get(i).getPatientId()));
        }

        patients.addAll(patEnt);

        return patients;
    }

    public int giveConsent(int doctorId, int reportId, int patientId, int radioId) {
        int userId = -1;
        if (doctorId != -1) {
            DoctorEntity doc = docDao.findById(doctorId).orElseThrow(
                    () -> new DoctoNotFoundException("No such doctor"));
            userId = doc.getUserId().getId();
        }
        if (radioId != -1) {
            RadiologistEntity rad = radDao.findById(radioId).orElseThrow(
                    () -> new DoctoNotFoundException("No such radiologist"));
            userId = rad.getUserId().getId();
        }
        ConsentEntity consent = new ConsentEntity();
        ReportEntity rep = repDao.findById(reportId).orElseThrow(
                () -> new ReportsNotFoundException("No such Report"));
        PatientEntity pat = patDao.findById(patientId).orElseThrow(
                () -> new PatientNotFoundException("No such patient"));
        CredentialsEntity cred = credDao.findById(userId).orElseThrow(
                () -> new UserNotFoundException("No such user"));
        consent.setViewerId(cred);
        consent.setReportId(rep);
        consent.setPatientId(pat);
        LocalDate currentDate = LocalDate.now();
        consent.setConsentDate(currentDate);
        Optional.ofNullable(consentDao.save(consent)).orElseThrow(
                () -> new GlobalException("Failed to save"));
        return userId;
    }

    public List<Report> getConsentedReports(GetConsentReportReq getConsentReportReq) {
        int patientId = getConsentReportReq.getPatientID();
        int doctorId = getConsentReportReq.getDoctorID();
        List<ConsentEntity> conEnt = new ArrayList<>();
        List<Report> reports = new ArrayList<>();
        conEnt = consentDao.findAllByPatientIdIdAndViewerIdId(patientId, doctorId).orElseThrow(
                () -> new GlobalException("Reports Not Found"));
        for (int i = 0; i < conEnt.size(); i++) {
            reports.add(mapToDomainReportEntity(conEnt.get(i).getReportId()));
        }
        return reports;
    }

    public int uploadAnnotation(int chatId) {
        AnnotatedImageEntity annotation = new AnnotatedImageEntity();
        ChatEntity chat = chatDao.findById(chatId).orElseThrow(
                () -> new GlobalException("No chat found"));
        annotation.setChatId(chat);
        AnnotatedImageEntity temp = annotationDao.save(annotation);
        return temp.getId();
    }

    public ReportEntity mapToRepEntity(UploadRequest upreq) {

        PatientEntity patEnt = patDao.findByEmail(upreq.getPatEmail());

        LabEntity labEnt = labDao.findByUserIdId(upreq.getLid()).orElseThrow(
                () -> new LabNotFoundException("No such Lab"));

        ReportEntity newRepEnt = new ReportEntity();

        newRepEnt.setDateOfIssue(upreq.getDateOfIssue());
        newRepEnt.setInitialRemarks(upreq.getInitialRemarks());
        newRepEnt.setReportType(upreq.getReportType());
        newRepEnt.setLabId(labEnt);
        newRepEnt.setPatientId(patEnt);

        return newRepEnt;
    }

    private List<Report> mapAllToDomainReportEntity(List<ReportEntity> reportList) {
        List<Report> domainReports = new ArrayList<>();

        for (ReportEntity reportEntity : reportList) {
            domainReports.add(mapToDomainReportEntity(reportEntity));
        }

        return domainReports;
    }

    private Report mapToDomainReportEntity(ReportEntity reportEntity) {
        Report report = new Report();
        report.setId(reportEntity.getId());
        report.setReportType(reportEntity.getReportType());
        report.setPatientId(reportEntity.getPatientId().getId());
        report.setLabId(reportEntity.getLabId().getId());
        report.setDateOfIssue(reportEntity.getDateOfIssue());
        report.setInitialRemarks(reportEntity.getInitialRemarks());
        return report;
    }

    private List<Consent> mapAllToDomainConsentEntity(List<ConsentEntity> ConsentEntityList) {
        List<Consent> ConsentList = new ArrayList<>();

        for (ConsentEntity consEnt : ConsentEntityList) {
            ConsentList.add(mapToDomainConsentEntity(consEnt));
        }
        return ConsentList;
    }

    private Consent mapToDomainConsentEntity(ConsentEntity consEnt) {
        Consent cons = new Consent();
        cons.setId(consEnt.getId());
        cons.setReportId(consEnt.getReportId().getId());
        cons.setViewerId(consEnt.getViewerId().getId());
        cons.setPatientId(consEnt.getPatientId().getId());
        cons.setConsentDate(consEnt.getConsentDate());

        return cons;
    }

    private Patient mapToDomainPatientEntity(PatientEntity patEnt) {
        Patient pat = new Patient();
        pat.setId(patEnt.getId());
        pat.setUserId(patEnt.getUserId().getId());
        pat.setFirstName(patEnt.getFirstName());
        pat.setMiddleName(patEnt.getMiddleName());
        pat.setLastName(patEnt.getLastName());
        pat.setDateOfBirth(patEnt.getDateOfBirth());
        pat.setGender(patEnt.getGender());
        pat.setAddress(patEnt.getAddress());
        pat.setCity(patEnt.getCity());
        pat.setState(patEnt.getState());
        pat.setPinCode(patEnt.getPinCode());
        pat.setEmail(patEnt.getEmail());
        pat.setPhoneNumber(patEnt.getPhoneNumber());
        pat.setEmergencyContact(patEnt.getEmergencyContact());
        pat.setBloodGroup(patEnt.getBloodGroup());
        pat.setHeight(patEnt.getHeight());
        pat.setWeight(patEnt.getWeight());
        pat.setProfilePhoto(patEnt.getProfilePhoto());
        pat.setAllergies(patEnt.getAllergies());
        pat.setChronicDiseases(patEnt.getChronicDiseases());
        pat.setCurrentMedication(patEnt.getCurrentMedication());
        pat.setDrinkingHabits(patEnt.getDrinkingHabits());
        pat.setFoodPreferences(patEnt.getFoodPreferences());
        pat.setPastMedication(patEnt.getPastMedication());
        pat.setSmokingHabits(patEnt.getSmokingHabits());

        return pat;
    }
}