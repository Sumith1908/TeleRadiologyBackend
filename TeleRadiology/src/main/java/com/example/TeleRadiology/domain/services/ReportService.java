package com.example.TeleRadiology.domain.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.example.TeleRadiology.data.dao.DoctorDao;
import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.DoctorEntity;
import org.springframework.stereotype.Service;

import com.example.TeleRadiology.data.dao.OtpDao;
import com.example.TeleRadiology.data.dao.PatientDao;
import com.example.TeleRadiology.data.entities.OtpEntity;
import com.example.TeleRadiology.data.entities.PatientEntity;
import com.example.TeleRadiology.domain.model.AnnotatedImage;
import com.example.TeleRadiology.domain.model.Consent;
import com.example.TeleRadiology.domain.model.Report;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.repositories.ReportRepository;
import com.example.TeleRadiology.dto.AnnotatedReportDTO;
import com.example.TeleRadiology.dto.ConsentResult;
import com.example.TeleRadiology.dto.GetAllAnnotationsRes;
import com.example.TeleRadiology.dto.GetAllReportsReq;
import com.example.TeleRadiology.dto.GetAllReportsRes;
import com.example.TeleRadiology.dto.GetAnnotationsReq;
import com.example.TeleRadiology.dto.GiveConsentReq;
import com.example.TeleRadiology.dto.GetConsentReportReq;
import com.example.TeleRadiology.dto.GiveConsentResult;
import com.example.TeleRadiology.dto.ReportResult;
import com.example.TeleRadiology.dto.SaveAnnotationReq;
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
    private final DoctorDao doctorDao;

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

        PatientEntity patientEntity = new PatientEntity();

        patientEntity=patientDao.findById(req.getPatientId()).orElse(null);

        CredentialsEntity credentialsEntity = new CredentialsEntity();

        credentialsEntity=patientEntity.getUserId();

        int credId = credentialsEntity.getId();

        if (verifyOTP(credId, req.getOtp())) {
            int viewerId = reportRepo.giveConsent(req.getDoctorId(), req.getReportId(), req.getPatientId(),
                    req.getRadiologistId());
            res.setSuccess(1);
            res.setViewerId(viewerId);
            PatientEntity pat = patientDao.findById(req.getPatientId()).orElse(null);
            if (pat != null) {
                if(req.getRadiologistId()!=-1 && req.getDoctorId()!=-1) {
                    DoctorEntity doctorEntity=new DoctorEntity();
                    doctorEntity=doctorDao.findById(req.getDoctorId()).orElse(null);
                    chatService.addChat(viewerId, doctorEntity.getUserId().getId(), req.getReportId());
                }
                else if(req.getRadiologistId()==-1 && req.getDoctorId()!=-1) {
                    chatService.addChat(viewerId, pat.getUserId().getId(), req.getReportId());
                }
            }
        }
         return res;
    }

    public int deleteConsent(RemoveConsentReq removeConsentReq) {

        PatientEntity patientEntity=new PatientEntity();

        patientEntity=patientDao.findById(removeConsentReq.getPatientId()).orElse(null);

        int credId=patientEntity.getUserId().getId();

        if (verifyOTP(credId, removeConsentReq.getOtp())) {
            reportRepo.removeConsent(removeConsentReq);
        }

        return 0;
    }

    public int uploadAnnotation(SaveAnnotationReq annotation) {
        int radId = annotation.getRadUserId();
        int docId = annotation.getDocUserId();
        int user1 = Math.min(radId, docId);
        int user2 = Math.max(radId, docId);
        int reportId = annotation.getReportId();
        int chatId = chatService.getChatId(user1, user2, reportId);
        int annotationId = reportRepo.uploadAnnotation(chatId);
        AnnotatedReportDTO annRep = new AnnotatedReportDTO();
        annRep.setAnnotatedImage(annotation.getAnnotatedImage());
        annRep.setAnnotationId(annotationId);
        imgService.callImageServerPost("/uploadAnnotatedReport", annRep,
                Boolean.class);
        return annotationId;
    }

    public GetAllAnnotationsRes getAllAnnotations(GetAnnotationsReq req) {
        int radId = req.getRadUserId();
        int docId = req.getDocUserId();
        int user1 = Math.min(radId, docId);
        int user2 = Math.max(radId, docId);
        int reportId = req.getReportId();
        int chatId = chatService.getChatId(user1, user2, reportId);
        List<AnnotatedImage> annotations = reportRepo.getAllAnnotations(chatId);
        GetAllAnnotationsRes res = new GetAllAnnotationsRes();
        List<AnnotatedReportDTO> list = new ArrayList<>();
        for (AnnotatedImage annotatedImage : annotations) {
            AnnotatedReportDTO dto = imgService.callImageServerGet("/getAnnotation/" + annotatedImage.getId(),
                    AnnotatedReportDTO.class);
            list.add(dto);
        }
        res.setAnnotations(list);
        return res;
    }

    public boolean verifyOTP(int credId, int sentOtp) {
        OtpEntity otp = otpDao.findByCredIdId(credId).orElseThrow(

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
        reportResult.setNotification(report.getNotification());
        reportResult.setId(report.getId());
        reportResult.setReportType(report.getReportType());
        reportResult.setPatientId(report.getPatientId());
        reportResult.setLabId(report.getLabId());
        reportResult.setDateOfIssue(report.getDateOfIssue());
        reportResult.setInitialRemarks(report.getInitialRemarks());

        return reportResult;
    }
}