package com.example.TeleRadiology.dto;

import java.util.List;

import lombok.Data;

@Data
public class GetChatsResult {
    List<PatientRes> pats;
    List<DocRes> docs;
    List<RadRes> rads;
}
