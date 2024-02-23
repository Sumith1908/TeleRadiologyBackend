package com.example.TeleRadiology.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeleRadiology.domain.TeleRadiologyService;
import com.example.TeleRadiology.dto.CredentialsRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teleRadiology")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*localhost*")
public class TeleRadiologyController {
    private final TeleRadiologyService teleRadService;

    @PostMapping("/loginCredentials")
    public int checkCredentials(@RequestBody CredentialsRequest credReq) {
        int id = teleRadService.checkCredentials(credReq.getEmail(), credReq.getRole(), credReq.getPassword());
        return id;
    }
}
