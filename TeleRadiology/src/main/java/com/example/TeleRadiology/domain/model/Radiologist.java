package com.example.TeleRadiology.domain.model;

import lombok.Data;

@Data
public class Radiologist {

    private int id;

    private int userId;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private int experience;

    private String highestEducation;

    private String profilePhoto;

    private int consent=0;

}