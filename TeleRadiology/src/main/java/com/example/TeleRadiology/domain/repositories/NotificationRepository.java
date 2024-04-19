package com.example.TeleRadiology.domain.repositories;

import java.util.List;

import com.example.TeleRadiology.domain.model.Notification;
import com.example.TeleRadiology.dto.AddNotificationReq;

public interface NotificationRepository {
List<Notification> geNotifications(int id);

Boolean addNotification(AddNotificationReq req);
    
}
