package com.example.TeleRadiology.exception;

public class RadiologistNotFoundException extends  RuntimeException {
    public RadiologistNotFoundException(String message) {
        super(message);
    }
}