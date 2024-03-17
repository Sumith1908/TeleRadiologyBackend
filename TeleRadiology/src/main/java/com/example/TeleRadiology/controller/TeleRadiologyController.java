package com.example.TeleRadiology.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeleRadiology.domain.services.TeleRadiologyService;
import com.example.TeleRadiology.dto.CredentialsRequest;
import com.example.TeleRadiology.dto.CredentialsResult;

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

    @PostMapping("/createPatientCred")
    public CredentialsResult createPatientCred(@RequestBody CredentialsRequest cred) {
        CredentialsResult credRes = teleRadService.addPatient(cred);
        return credRes;
    }
}
