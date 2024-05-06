package com.example.TeleRadiology.dto;

import java.util.List;

import lombok.Data;

@Data
public class GetAllAnnotationsRes {
    List<AnnotatedReportDTO> annotations;
}
