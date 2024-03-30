package com.example.imageStorage.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.imageStorage.domain.ImageStorageService;
import com.example.imageStorage.dto.AnnotatedReportDTO;
import com.example.imageStorage.dto.ProfilePicDTO;
import com.example.imageStorage.dto.ReportDTO;
import com.example.imageStorage.exception.GlobalException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/images")
@CrossOrigin(originPatterns = "*localhost*")
public class ImageController {
    private final ImageStorageService imageService;

    @PostMapping("/uploadReport")
    public boolean addReport(@RequestBody ReportDTO addRep) {
        try {
            imageService.addReport(addRep.getReport(), addRep.getReportId());
        } catch (Exception e) {
            throw new GlobalException("Unauthorized");
        }
        return true;
    }

    @PostMapping("/uploadAnnotatedReport")
    public boolean addAnnotatedReport(@RequestBody AnnotatedReportDTO addRep) {
        return imageService.addAnnotatedReport(addRep.getAnnotation(), addRep.getAnnotationId());
    }

    @PostMapping("/uploadProfilePic")
    public boolean addProfilePic(@RequestBody ProfilePicDTO addProfile) {
        return imageService.addProfilePic(addProfile.getProfilePic(), addProfile.getUserId());
    }

    @GetMapping("/getReport/{id}")
    public ReportDTO getReport(@PathVariable int id) {
        return imageService.getReport(id);
    }

    @GetMapping("/getAnnotation/{id}")
    public AnnotatedReportDTO getAnnotation(@PathVariable int id) {
        return imageService.getAnnotation(id);
    }

    @GetMapping("/getProfilePic/{id}")
    public ProfilePicDTO getProfilePic(@PathVariable int id) {
        return imageService.getProfilePic(id);
    }

    @GetMapping("/test")
    public String test() {
        return "Hello";
    }
}
