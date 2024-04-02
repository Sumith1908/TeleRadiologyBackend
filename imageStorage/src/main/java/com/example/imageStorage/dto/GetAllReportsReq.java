package com.example.imageStorage.dto;

import java.util.List;

import lombok.Data;

@Data
public class GetAllReportsReq {
    List<Integer> reportIds;
}
