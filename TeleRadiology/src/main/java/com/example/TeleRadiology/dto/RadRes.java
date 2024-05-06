package com.example.TeleRadiology.dto;

import java.util.List;

import lombok.Data;

@Data
public class RadRes {
    String name;
    int userId;
    List<Integer> reports;
    String profileImage;
}
