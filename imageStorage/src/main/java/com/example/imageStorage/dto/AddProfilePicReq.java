package com.example.imageStorage.dto;

import lombok.Data;

@Data
public class AddProfilePicReq {
    private int userId;

    private String profilePic;
}
