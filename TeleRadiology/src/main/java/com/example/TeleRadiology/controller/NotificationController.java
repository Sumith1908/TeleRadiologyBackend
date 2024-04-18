package com.example.TeleRadiology.controller;

import javax.management.NotificationBroadcaster;
import javax.management.remote.NotificationResult;

import com.example.TeleRadiology.dto.AddNotificationReq;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeleRadiology.domain.services.NotificationService;
import com.example.TeleRadiology.dto.DetailsRequest;
import com.example.TeleRadiology.dto.NotificationsRes;
import com.example.TeleRadiology.dto.PatientResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teleRadiology")
@RequiredArgsConstructor
// @CrossOrigin(originPatterns = "*localhost*")
public class NotificationController {
    private final NotificationService notService;

    @GetMapping("/getNotifications/{id}")
    public NotificationsRes getNotifications(@PathVariable int id) {
        NotificationsRes res = new NotificationsRes();
        res.setNotifications(notService.geNotifications(id));
        return res;

    }

    @PostMapping("/addNotification")
    public Boolean addNotification(AddNotificationReq req) {
        notService.addNotification(req);
        return true;
    }

}
