package com.example.TeleRadiology.exception;

public class LabNotFoundException extends RuntimeException {
    public LabNotFoundException(String message) {
        super(message);
    }
}
