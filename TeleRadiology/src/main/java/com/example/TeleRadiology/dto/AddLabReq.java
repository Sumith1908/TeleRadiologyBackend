package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class AddLabReq {
    String email;
    String password;
    String role;

    private String name;
    private String address;
    private String city;
    private String state;
    private String pinCode;
    private String phoneNumber;
}
