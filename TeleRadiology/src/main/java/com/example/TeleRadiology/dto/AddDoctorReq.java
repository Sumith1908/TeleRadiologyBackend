package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class AddDoctorReq {
//    String email;
    String password;
    String role;

    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String hospitalAddress;
    private String hospitalCity;
    private String hospitalState;
    private String hospitalPinCode;
    private String hospitalEmail;
    private String hospitalPhoneNumber;
    private int experience;
    private String highestEducation;
    private String type;
    private String profilePhoto;
}
