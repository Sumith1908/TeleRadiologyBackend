package com.example.TeleRadiology.dto;

import java.util.List;

import com.example.TeleRadiology.domain.model.Report;
import lombok.Data;

@Data
public class DocRes {
    String name;
    int userId;
    List<Integer> reports;
    String profileImage;
    List<Report> reportsWithDetails;
}