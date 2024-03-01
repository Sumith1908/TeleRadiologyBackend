package com.example.imageStorage.data.collections;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "profilePics")
public class ProfilePicDocument {
    @Field("user_id")
    private int userId;

    @Field("profic_pic")
    private String profilePic;
}
