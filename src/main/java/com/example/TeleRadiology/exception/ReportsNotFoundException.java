package com.example.TeleRadiology.exception;

public class ReportsNotFoundException extends RuntimeException {
    public ReportsNotFoundException(String message) {
        super(message);
    }
}
