package com.example.TeleRadiology.dto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UploadRequest {
    LocalDate dateOfIssue;
    String initialRemarks;
    String reportType;
    int lid;
    String patEmail;
}