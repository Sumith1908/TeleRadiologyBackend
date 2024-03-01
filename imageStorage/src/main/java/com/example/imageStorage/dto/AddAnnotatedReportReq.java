package com.example.imageStorage.dto;

import lombok.Data;

@Data
public class AddAnnotatedReportReq {
    private int annotationId;

    private String annotation;
}
