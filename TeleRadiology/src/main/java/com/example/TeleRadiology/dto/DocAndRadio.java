package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class DocAndRadio {
    private String name;
    private String role;
    private int id;
    private int credId;
    private int consent=0;
}