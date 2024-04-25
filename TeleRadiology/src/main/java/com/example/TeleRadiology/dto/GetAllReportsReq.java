package com.example.TeleRadiology.dto;

import java.util.List;

import lombok.Data;

@Data
public class GetAllReportsReq {
    List<Integer> reportIds;
}
