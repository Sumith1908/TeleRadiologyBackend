package com.example.TeleRadiology.domain.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.TeleRadiology.data.dao.DoctorDao;
import com.example.TeleRadiology.data.dao.NotificationDao;
import com.example.TeleRadiology.data.dao.RadiologistDao;
import com.example.TeleRadiology.data.dao.ReportDao;
import com.example.TeleRadiology.data.entities.NotificationEntity;
import com.example.TeleRadiology.domain.model.Notification;
import com.example.TeleRadiology.exception.GlobalException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationRepositoryImplementation implements NotificationRepository {
    private final NotificationDao notDao;

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

}
