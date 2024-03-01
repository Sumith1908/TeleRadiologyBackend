package com.example.imageStorage.domain.model;

import lombok.Data;

@Data
public class Annotation {
    private int id;

    private int annotationId;

    private byte[] annotation;
}
