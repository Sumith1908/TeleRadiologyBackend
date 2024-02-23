package com.example.TeleRadiology.domain.model;

import lombok.Data;

@Data
public class Lab {

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
