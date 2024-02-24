package com.example.TeleRadiology.dto;

import lombok.Data;

@Data

public class RadiologistResult {
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

}
