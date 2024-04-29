package com.example.TeleRadiology.dto;

import java.util.List;

import lombok.Data;

@Data
public class AnnotationData {
    private String image;
    private List<String> annotations;
}
