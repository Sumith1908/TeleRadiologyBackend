package com.example.TeleRadiology.domain.repositories;

import java.util.List;

import com.example.TeleRadiology.domain.model.Notification;

public interface NotificationRepository {
List<Notification> geNotifications(int id);
    
}
