package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class LabResult {
    private int id;

    private String name;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    private int userId;

    private double rating;

    private String phoneNumber;

}
