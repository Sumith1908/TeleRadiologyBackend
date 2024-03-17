package com.example.TeleRadiology.dto;

import java.util.List;

import lombok.Data;

@Data
public class ReportList {
    List<ReportResult> reports;
}
