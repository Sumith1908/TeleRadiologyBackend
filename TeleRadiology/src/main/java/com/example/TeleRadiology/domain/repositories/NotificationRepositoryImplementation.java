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
    public List<Notification> geNotifications(int id) {
        List<NotificationEntity> notifications = notDao.findAllByReciverIdId(id).orElse(null);
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
        return notification;
    }

    public Boolean addNotification(AddNotificationReq req) {
        notDao.save(mapToEntityNotification(req));
        return true;
    }

    private NotificationEntity mapToEntityNotification(AddNotificationReq req) {
        NotificationEntity notEnt = new NotificationEntity();
        PatientEntity patEnt = patientDao.findById(req.getPatientId()).orElse(null);
        DoctorEntity docEnt = doctorDao.findById(req.getDoctorId()).orElse(null);
        RadiologistEntity radEnt = radiologistDao.findById(req.getRadiologistId()).orElse(null);
        ReportEntity repEnt = reportDao.findById(req.getReportId()).orElse(null);
        CredentialsEntity credEnt = credentialsDao.findById(req.getReciverId()).orElse(null);

        notEnt.setDoctorId(docEnt);
        notEnt.setPatiientId(patEnt);
        notEnt.setReciverId(credEnt);
        notEnt.setReportId(repEnt);
        notEnt.setRadiologistId(radEnt);
        return notEnt;
    }

}
