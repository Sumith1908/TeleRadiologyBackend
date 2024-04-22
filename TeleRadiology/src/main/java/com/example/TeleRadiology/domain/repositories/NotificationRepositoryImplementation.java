package com.example.TeleRadiology.domain.repositories;

import java.util.ArrayList;
import java.util.List;

import com.example.TeleRadiology.data.dao.*;
import com.example.TeleRadiology.data.entities.*;
import com.example.TeleRadiology.dto.AddNotificationReq;
import org.springframework.stereotype.Component;

import com.example.TeleRadiology.domain.model.Notification;
import com.example.TeleRadiology.exception.GlobalException;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryImplementation implements NotificationRepository {
    private final NotificationDao notDao;
    private final DoctorDao doctorDao;
    private final PatientDao patientDao;
    private final RadiologistDao radiologistDao;
    private final ReportDao reportDao;
    private final CredentialsDao credentialsDao;

    @Override
    public List<Notification> geNotifications(int credId, int reportId) {
        List<NotificationEntity> notifications = notDao.findAllByReciverIdIdAndReportIdId(credId, reportId)
                .orElse(null);
        List<Notification> notifications1 = new ArrayList<>();
        if (notifications == null) {
            return new ArrayList<>();

        }
        for (NotificationEntity notification : notifications) {
            notifications1.add(mapToDomainNotificationEntity(notification));

        }
        return notifications1;
    }

    private Notification mapToDomainNotificationEntity(NotificationEntity notEnt) {
        Notification notification = new Notification();
        notification.setDoctorId(notEnt.getDoctorId().getId());
        notification.setRadiologistId(notEnt.getRadiologistId().getId());
        notification.setReportId(notEnt.getReportId().getId());
        notification.setId(notEnt.getId());
        return notification;
    }

    public Boolean addNotification(AddNotificationReq req) {
        notDao.save(mapToEntityNotification(req));
        return true;
    }

    @Transactional
    public int deleteNotification(int id) {
        notDao.deleteById(id);
        return 1;
    }

    private NotificationEntity mapToEntityNotification(AddNotificationReq req) {
        NotificationEntity notEnt = new NotificationEntity();
        PatientEntity patEnt = patientDao.findById(req.getPatientId())
                .orElseThrow(() -> new GlobalException("Patient not found"));
        DoctorEntity docEnt = doctorDao.findById(req.getDoctorId())
                .orElseThrow(() -> new GlobalException("Doctor not found"));
        RadiologistEntity radEnt = radiologistDao.findById(req.getRadiologistId())
                .orElseThrow(() -> new GlobalException("Radiologist not found"));
        ReportEntity repEnt = reportDao.findById(req.getReportId())
                .orElseThrow(() -> new GlobalException("Report not found"));
        CredentialsEntity credEnt = credentialsDao.findById(req.getReciverId())
                .orElseThrow(() -> new GlobalException("Reciver not found"));

        notEnt.setDoctorId(docEnt);
        notEnt.setPatientId(patEnt);
        notEnt.setReciverId(credEnt);
        notEnt.setReportId(repEnt);
        notEnt.setRadiologistId(radEnt);
        return notEnt;
    }

}