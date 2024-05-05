package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class AddRadiologistReq {
    String password;
    String role;

    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int experience;
    private String highestEducation;
    private String profilePhoto;
}
