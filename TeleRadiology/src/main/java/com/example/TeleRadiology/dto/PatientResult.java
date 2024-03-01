package com.example.TeleRadiology.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PatientResult {
    private int id;

    private int userId;

    private String firstName;

    private String middleName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    private String email;

    private String phoneNumber;

    private String emergencyContact;

    private String bloodGroup;

    private double height;

    private double weight;

    private String profilePhoto;

}
