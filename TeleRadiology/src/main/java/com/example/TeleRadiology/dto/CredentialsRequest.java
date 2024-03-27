package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class CredentialsRequest {
    String email;
    String password;
    String role;
}
