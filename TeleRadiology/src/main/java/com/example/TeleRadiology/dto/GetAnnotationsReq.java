package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class GetAnnotationsReq {
    private int docUserId;
    private int radUserId;
    private int reportId;
}
