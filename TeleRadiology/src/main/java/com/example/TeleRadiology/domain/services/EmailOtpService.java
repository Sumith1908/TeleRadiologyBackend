package com.example.TeleRadiology.domain.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.TeleRadiology.domain.repositories.ReportRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EmailOtpService {
    private final JavaMailSender mailSender;
    private final ReportRepository rep;

    public void sendEmailOtp(String sub, int otp, int id) {
        String to = rep.setOtp(otp, id);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("projectt575@gmail.com");
        message.setTo(to);
        message.setText("Your otp for consent aproval is " + Integer.toString(otp));
        message.setSubject(sub);
        mailSender.send(message);
    }
}
