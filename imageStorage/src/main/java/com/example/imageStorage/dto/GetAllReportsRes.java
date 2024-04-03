package com.example.imageStorage.dto;

import java.util.List;

import lombok.Data;

@Data
public class GetAllReportsRes {
    List<ReportDTO> reports;
}
