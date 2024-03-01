package com.example.imageStorage.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.imageStorage.domain.ImageStorageService;
import com.example.imageStorage.dto.AddAnnotatedReportReq;
import com.example.imageStorage.dto.AddProfilePicReq;
import com.example.imageStorage.dto.AddReportReq;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/images")
@CrossOrigin(originPatterns = "*localhost*")
public class ImageController {
    private final ImageStorageService imageService;

    @PostMapping("/uploadReport")
    public boolean addReport(@RequestBody AddReportReq addRep) {
        return imageService.addReport(addRep.getImage(), addRep.getId());
    }

    @PostMapping("/uploadAnnotatedReport")
    public boolean addAnnotatedReport(@RequestBody AddAnnotatedReportReq addRep) {
        return imageService.addAnnotatedReport(addRep.getAnnotation(), addRep.getAnnotationId());
    }

    @PostMapping("/uploadProfilePic")
    public boolean addProfilePic(@RequestBody AddProfilePicReq addProfile) {
        return imageService.addProfilePic(addProfile.getProfilePic(), addProfile.getUserId());
    }
}
