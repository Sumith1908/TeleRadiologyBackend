package com.example.TeleRadiology.controller;

import org.springframework.web.bind.annotation.*;

import com.example.TeleRadiology.domain.services.NotificationService;
import com.example.TeleRadiology.dto.AddNotificationReq;
import com.example.TeleRadiology.dto.GetNotificationReq;
import com.example.TeleRadiology.dto.NotificationsRes;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teleRadiology")
@RequiredArgsConstructor
// @CrossOrigin(originPatterns = "*localhost*")
public class NotificationController {
    private final NotificationService notService;

    @PostMapping("/getNotifications")
    public NotificationsRes getNotifications(@RequestBody GetNotificationReq req) {
        NotificationsRes res = new NotificationsRes();
        res.setNotifications(notService.geNotifications(req.getCredId(), req.getReportId()));
        return res;
    }

    @PostMapping("/addNotification")
    public Boolean addNotification(@RequestBody AddNotificationReq req) {
        notService.addNotification(req);
        return true;
    }

    @GetMapping("/deleteNotification/{id}")
    public int deleteNotification(@PathVariable("id") int id) {
        notService.deleteNotification(id);
        return 1;
    }
}