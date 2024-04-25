package com.example.TeleRadiology.dto;

import java.util.List;

import lombok.Data;

@Data
public class GetAllReportsRes {
    List<ReportDTO> reports;
}
