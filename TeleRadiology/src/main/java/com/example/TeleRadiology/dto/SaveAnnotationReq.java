package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class SaveAnnotationReq {
    private String annotatedImage;
    private int docUserId;
    private int radUserId;
    private int reportId;
}
