package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class DoctorResult {
    private int id;

    private int userId;

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

    private double rating;

    private String highestEducation;

    private String type;

    private String profilePhoto;
}
