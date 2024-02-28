package com.example.TeleRadiology.data;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.TeleRadiology.data.dao.ConsentDao;
import com.example.TeleRadiology.data.dao.CredentialsDao;
import com.example.TeleRadiology.data.dao.DoctorDao;
import com.example.TeleRadiology.data.dao.LabDao;
import com.example.TeleRadiology.data.dao.PatientDao;
import com.example.TeleRadiology.data.dao.RadiologistDao;
import com.example.TeleRadiology.data.dao.ReportDao;
import com.example.TeleRadiology.data.dao.RoleDao;
import com.example.TeleRadiology.data.entities.ConsentEntity;
import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.DoctorEntity;
import com.example.TeleRadiology.data.entities.LabEntity;
import com.example.TeleRadiology.data.entities.PatientEntity;
import com.example.TeleRadiology.data.entities.RadiologistEntity;
import com.example.TeleRadiology.data.entities.ReportEntity;
import com.example.TeleRadiology.data.entities.RoleEntity;
import com.example.TeleRadiology.domain.TeleRadiologyRepository;
import com.example.TeleRadiology.domain.model.Consent;
import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.domain.model.Lab;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.model.Radiologist;
import com.example.TeleRadiology.domain.model.Report;
import com.example.TeleRadiology.dto.CredentialsRequest;
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
public class TeleRadiologyRepositoryImplementation implements TeleRadiologyRepository {
    private final CredentialsDao credDao;
    private final RoleDao roleDao;
    private final PatientDao patDao;
    private final DoctorDao docDao;
    private final RadiologistDao radDao;
    private final LabDao labDao;
    private final ReportDao repDao;
    private final ConsentDao consentDao;

    @Override
    public Credentials checkLoginCredentials(String email, int role) {
        // RoleEntity roleEntity = roleDao.findById(role).orElse(null);
        // Credentials cred = new Credentials();
        // if (roleEntity != null) {
        // cred.setRole(role);
        // CredentialsEntity credEnt = credDao.findByEmailAndRole(email,
        // roleEntity).orElse(null);
        // if (credEnt != null) {
        // return mapToDomainCredentialsEntity(credEnt);
        // } else {
        // return cred;
        // }
        // return cred;
        RoleEntity roleEntity = roleDao.findById(role).orElseThrow(
                () -> new GlobalException("Wrong role"));
        CredentialsEntity credEnt = credDao.findByEmailAndRole(email, roleEntity).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        return mapToDomainCredentialsEntity(credEnt);
    }

    public Patient getPatient(int id) {
        PatientEntity patEnt = patDao.findByUserIdId(id).orElseThrow(
                () -> new PatientNotFoundException("No such patient"));
        // System.out.println(patEnt.getAddress());
        return mapToDomainPatientEntity(patEnt);
    }

    public Doctor getDoctor(int id) {
        DoctorEntity docEnt = docDao.findByUserIdId(id).orElseThrow(
                () -> new PatientNotFoundException("No such doctor"));
        return mapToDomainDoctorEntity(docEnt);
    }

    public Radiologist getRadiologist(int id) {
        RadiologistEntity radEnt = radDao.findByUserIdId(id).orElseThrow(
                () -> new DoctoNotFoundException("No such radiologist"));
        return mapToDomainRadiologistEntity(radEnt);
    }

    public Lab getLab(int id) {
        LabEntity labEnt = labDao.findByUserIdId(id).orElseThrow(
                () -> new LabNotFoundException("No such lab"));
        return mapToDomainLabEntity(labEnt);
    }

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

    public int addPatient(CredentialsRequest cred) {
        if (credDao.existsByEmail(cred.getEmail())) {
            throw new GlobalException("EmailID Already Exists");
        }
        CredentialsEntity newUser = credDao.save(mapToCredEntity(cred));

        return newUser.getId();
    }

    public int uploadPatientReport(UploadRequest upreq) {
        if (!patDao.existsByEmail(upreq.getPatEmail()))
            throw new PatientNotFoundException("No such patient");
        else
            repDao.save(mapToRepEntity(upreq));

        return mapToRepEntity(upreq).getId();
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

    public CredentialsEntity mapToCredEntity(CredentialsRequest cred) {

        RoleEntity roleEntity = roleDao.findById(cred.getRole()).orElseThrow(
                () -> new GlobalException("Wrong role"));
        CredentialsEntity newCred = new CredentialsEntity();
        newCred.setEmail(cred.getEmail());
        newCred.setPassword(cred.getPassword());
        newCred.setActive(1);
        newCred.setRole(roleEntity);

        return newCred;
    }

    private Credentials mapToDomainCredentialsEntity(CredentialsEntity credEntity) {
        Credentials cred = new Credentials();
        cred.setId(credEntity.getId());
        cred.setEmail(credEntity.getEmail());
        cred.setActive(credEntity.getActive());
        cred.setPassword(credEntity.getPassword());
        cred.setRole(credEntity.getRole().getId());
        return cred;
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

        return pat;
    }

    private Doctor mapToDomainDoctorEntity(DoctorEntity docEnt) {
        Doctor doc = new Doctor();
        doc.setId(docEnt.getId());
        doc.setUserId(docEnt.getUserId().getId());
        doc.setFirstName(docEnt.getFirstName());
        doc.setMiddleName(docEnt.getMiddleName());
        doc.setLastName(docEnt.getLastName());
        doc.setGender(docEnt.getGender());
        doc.setHospitalAddress(docEnt.getHospitalAddress());
        doc.setHospitalCity(docEnt.getHospitalCity());
        doc.setHospitalState(docEnt.getHospitalState());
        doc.setHospitalPinCode(docEnt.getHospitalPinCode());
        doc.setHospitalEmail(docEnt.getHospitalEmail());
        doc.setHospitalPhoneNumber(docEnt.getHospitalPhoneNumber());
        doc.setExperience(docEnt.getExperience());
        doc.setRating(docEnt.getRating());
        doc.setHighestEducation(docEnt.getHighestEducation());
        doc.setType(docEnt.getType());
        doc.setProfilePhoto(docEnt.getProfilePhoto());

        return doc;
    }

    private Radiologist mapToDomainRadiologistEntity(RadiologistEntity radEnt) {
        Radiologist rad = new Radiologist();
        rad.setId(radEnt.getId());
        rad.setUserId(radEnt.getUserId().getId());
        rad.setFirstName(radEnt.getFirstName());
        rad.setMiddleName(radEnt.getMiddleName());
        rad.setLastName(radEnt.getLastName());
        rad.setEmail(radEnt.getEmail());
        rad.setPhoneNumber(radEnt.getPhoneNumber());
        rad.setExperience(radEnt.getExperience());
        rad.setHighestEducation(radEnt.getHighestEducation());
        rad.setProfilePhoto(radEnt.getProfilePhoto());

        return rad;
    }

    private Lab mapToDomainLabEntity(LabEntity labEnt) {
        Lab lab = new Lab();
        lab.setId(labEnt.getId());
        lab.setName(labEnt.getName());
        lab.setAddress(labEnt.getAddress());
        lab.setCity(labEnt.getCity());
        lab.setState(labEnt.getState());
        lab.setPinCode(labEnt.getPinCode());
        lab.setUserId(labEnt.getUserId().getId());
        lab.setRating(labEnt.getRating());
        lab.setPhoneNumber(labEnt.getPhoneNumber());

        return lab;
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

    private Consent mapToDomainConsentEntity(ConsentEntity consEnt) {
        Consent cons = new Consent();
        cons.setId(consEnt.getId());
        cons.setReportId(consEnt.getReportId().getId());
        cons.setViewerId(consEnt.getViewerId().getId());
        cons.setPatientId(consEnt.getPatientId().getId());
        cons.setConsentDate(consEnt.getConsentDate());

        return cons;
    }

}
