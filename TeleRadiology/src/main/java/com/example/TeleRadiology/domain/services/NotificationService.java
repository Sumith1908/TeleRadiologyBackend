package com.example.TeleRadiology.domain.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.TeleRadiology.data.dao.DoctorDao;
import com.example.TeleRadiology.data.dao.RadiologistDao;
import com.example.TeleRadiology.data.dao.ReportDao;
import com.example.TeleRadiology.data.entities.DoctorEntity;
import com.example.TeleRadiology.data.entities.NotificationEntity;
import com.example.TeleRadiology.data.entities.RadiologistEntity;
import com.example.TeleRadiology.data.entities.ReportEntity;
import com.example.TeleRadiology.domain.model.Notification;
import com.example.TeleRadiology.domain.model.Radiologist;
import com.example.TeleRadiology.domain.repositories.NotificationRepository;
import com.example.TeleRadiology.dto.NotificationDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notRepo;
    private final DoctorDao docDao;
    private final ReportDao repDao;
    private final RadiologistDao radDao;
    public List<NotificationDto>  geNotifications(int id) {
        List<NotificationDto> notifications=new ArrayList<>();
        List<Notification> notifi=notRepo.geNotifications(id);
        for (Notification notif : notifi) {
            notifications.add(mapToDtoNotification(notif));
            
            
        }
        return notifications;
    }
    private NotificationDto mapToDtoNotification (Notification notification)
    {
        NotificationDto notification1=new NotificationDto();
        DoctorEntity doc=docDao.findById(notification.getDoctorId()).orElse(null);
        RadiologistEntity rad=radDao.findById(notification.getRadiologistId()).orElse(null);
        ReportEntity rep=repDao.findById(notification.getReportId()).orElse(null);
        if(doc!=null) notification1.setDoctor(doc.getFirstName()+" "+doc.getMiddleName()+" "+doc.getLastName());
        if(rad!=null) notification1.setRadiologist(rad.getFirstName()+" "+rad.getMiddleName()+" "+rad.getLastName());
        if(rep!=null) notification1.setDoctor(rep.getReportType());
        return notification1;

    }
    
}
