package com.example.TeleRadiology.dto;

import lombok.Data;

@Data
public class Response<T> {
    int success = 0;
    T data;
    String message = "";
}
