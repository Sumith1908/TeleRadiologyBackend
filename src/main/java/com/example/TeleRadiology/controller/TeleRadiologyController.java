package com.example.TeleRadiology.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeleRadiology.domain.TeleRadiologyService;
import com.example.TeleRadiology.dto.CredentialsRequest;
import com.example.TeleRadiology.dto.CredentialsResult;
import com.example.TeleRadiology.dto.DetailsRequest;
import com.example.TeleRadiology.dto.DoctorResult;
import com.example.TeleRadiology.dto.LabResult;
import com.example.TeleRadiology.dto.PatientResult;
import com.example.TeleRadiology.dto.RadiologistResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teleRadiology")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*localhost*")
public class TeleRadiologyController {
    private final TeleRadiologyService teleRadService;

    @PostMapping("/loginCredentials")
    public CredentialsResult checkCredentials(@RequestBody CredentialsRequest credReq) {
        CredentialsResult credRes = teleRadService.checkCredentials(credReq.getEmail(), credReq.getRole(),
                credReq.getPassword());
        return credRes;
    }

    @PostMapping("/getPatient")
    public PatientResult getPatient(@RequestBody DetailsRequest detReq) {
        PatientResult pat = teleRadService.getPatient(detReq.getId());
        return pat;
    }

    @PostMapping("/getDoctor")
    public DoctorResult getDoctor(@RequestBody DetailsRequest detReq) {
        DoctorResult doc = teleRadService.getDoctor(detReq.getId());
        return doc;
    }

    @PostMapping("/getRadiologist")
    public RadiologistResult getRadiologist(@RequestBody DetailsRequest detReq) {
        RadiologistResult rad = teleRadService.getRadiologist(detReq.getId());
        return rad;
    }

    @PostMapping("/getLab")
    public LabResult getLab(@RequestBody DetailsRequest detReq) {
        LabResult lab = teleRadService.getLab(detReq.getId());
        return lab;
    }
}
