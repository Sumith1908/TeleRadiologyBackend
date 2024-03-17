package com.example.TeleRadiology.domain.repositories;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.TeleRadiology.data.dao.ConsentDao;
import com.example.TeleRadiology.data.dao.DoctorDao;
import com.example.TeleRadiology.data.dao.LabDao;
import com.example.TeleRadiology.data.dao.PatientDao;
import com.example.TeleRadiology.data.dao.ReportDao;
import com.example.TeleRadiology.data.entities.ConsentEntity;
import com.example.TeleRadiology.data.entities.DoctorEntity;
import com.example.TeleRadiology.data.entities.LabEntity;
import com.example.TeleRadiology.data.entities.PatientEntity;
import com.example.TeleRadiology.data.entities.ReportEntity;
import com.example.TeleRadiology.domain.model.Consent;
import com.example.TeleRadiology.domain.model.Report;
import com.example.TeleRadiology.dto.UploadRequest;
import com.example.TeleRadiology.exception.ConsentNotFoundException;
import com.example.TeleRadiology.exception.DoctoNotFoundException;
import com.example.TeleRadiology.exception.GlobalException;
import com.example.TeleRadiology.exception.LabNotFoundException;
import com.example.TeleRadiology.exception.PatientNotFoundException;
import com.example.TeleRadiology.exception.ReportsNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReportRepositoryImplementation implements ReportRepository {

    private final ReportDao repDao;
    private final ConsentDao consentDao;
    private final PatientDao patDao;
    private final DoctorDao docDao;
    private final LabDao labDao;

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
        if (!patDao.existsByEmail(upreq.getPatEmail()))
            throw new PatientNotFoundException("No such patient");
        else
            repDao.save(mapToRepEntity(upreq));

        return mapToRepEntity(upreq).getId();
    }

    public int giveConsent(int doctorId, int reportId, int patientId) {
        DoctorEntity doc = docDao.findById(doctorId).orElseThrow(
                () -> new DoctoNotFoundException("No such doctor"));
        ConsentEntity consent = new ConsentEntity();
        ReportEntity rep = repDao.findById(reportId).orElseThrow(
                () -> new ReportsNotFoundException("No such Report"));
        PatientEntity pat = patDao.findById(patientId).orElseThrow(
                () -> new PatientNotFoundException("No such patient"));
        consent.setPatientId(pat);
        consent.setViewerId(doc.getUserId());
        consent.setReportId(rep);
        LocalDate currentDate = LocalDate.now();
        consent.setConsentDate(currentDate);
        Optional.ofNullable(consentDao.save(consent)).orElseThrow(
                () -> new GlobalException("Failed to save"));
        return doc.getUserId().getId();
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