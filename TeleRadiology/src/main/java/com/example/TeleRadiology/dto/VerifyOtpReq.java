package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class VerifyOtpReq {
    int credId;
    int otp;
}