package com.example.TeleRadiology.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeleRadiology.domain.services.TeleRadiologyService;
import com.example.TeleRadiology.dto.CredentialsRequest;
import com.example.TeleRadiology.dto.CredentialsResult;
import com.example.TeleRadiology.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teleRadiology")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*localhost*")
public class TeleRadiologyController {
    private final TeleRadiologyService teleRadService;
    private final JwtService jwt;

    @PostMapping("/loginCredentials")
    public CredentialsResult checkCredentials(@RequestBody CredentialsRequest credReq) {
        // CredentialsResult credRes =
        // teleRadService.checkCredentials(credReq.getEmail(), credReq.getRole(),
        // credReq.getPassword());
        // return credRes;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = jwt.generateToken(authentication);
        CredentialsResult cred = new CredentialsResult();
        cred.setToken(token);
        return cred;
    }

    @PostMapping("/createPatientCred")
    public CredentialsResult createPatientCred(@RequestBody CredentialsRequest cred) {
        CredentialsResult credRes = teleRadService.addPatient(cred);
        return credRes;
    }
}
