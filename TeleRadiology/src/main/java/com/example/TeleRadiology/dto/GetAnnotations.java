package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class GetAnnotations {
    private int docUserId;
    private int radUserId;
    private int reportId;
}
