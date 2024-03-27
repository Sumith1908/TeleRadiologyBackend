package com.example.TeleRadiology.controller;

import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        CredentialsResult credRes = teleRadService.checkCredentials(credReq.getEmail(), credReq.getRole(),
                credReq.getPassword());
        String role = credReq.getRole();
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        Authentication auth = new UsernamePasswordAuthenticationToken(
                credReq.getEmail(), null, authorities);
        String token = jwt.generateToken(auth);
        credRes.setToken(token);
        return credRes;
    }

    @PostMapping("/createPatientCred")
    public CredentialsResult createPatientCred(@RequestBody CredentialsRequest cred) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        cred.setPassword(encoder.encode(cred.getPassword()));
        CredentialsResult credRes = teleRadService.addPatient(cred);
        return credRes;
    }
}
