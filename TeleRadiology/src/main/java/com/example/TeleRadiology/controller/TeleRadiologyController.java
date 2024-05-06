package com.example.TeleRadiology.controller;

import java.util.ArrayList;

import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.domain.services.EmailOtpService;
import com.example.TeleRadiology.domain.services.ReportService;
import com.example.TeleRadiology.dto.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.example.TeleRadiology.domain.services.TeleRadiologyService;
import com.example.TeleRadiology.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teleRadiology")
@RequiredArgsConstructor
// @CrossOrigin(originPatterns = "*localhost*")
public class TeleRadiologyController {
    // private static final Logger logger =
    // LoggerFactory.getLogger(TeleRadiologyController.class);
    private final TeleRadiologyService teleRadService;
    private final ReportService reportService;
    private final EmailOtpService email;
    private final JwtService jwt;

    @PostMapping("/loginCredentials")
    public CredentialsResult checkCredentials(@RequestBody CredentialsRequest credReq) {
        // logger.info("User '{}' tried to login", credReq.getEmail());
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
        CredentialsResult credRes = teleRadService.addPatient(cred);
        return credRes;
    }

    @GetMapping(value = "/checkEmail/{id}")
    public Credentials checkEmail(@PathVariable("id") String emailId) {
        Credentials res=new Credentials();
        res=teleRadService.checkEmail(emailId);
        return res;
    }

    @PostMapping(value = "/changePassword")
    public void changePassword(@RequestBody ChangePasswordReq changePasswordReq) {
        teleRadService.changePassword(changePasswordReq);
    }

    @PostMapping(value = "/verifyOtp")
    public void verifyOtp(@RequestBody VerifyOtpReq verifyOtpReq) {
        reportService.verifyOTP(verifyOtpReq.getCredId(), verifyOtpReq.getOtp());
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutReq req) {
        teleRadService.deleteToken(req.getToken());
    }
}