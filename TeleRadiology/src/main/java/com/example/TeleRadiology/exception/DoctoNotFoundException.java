package com.example.TeleRadiology.exception;

public class DoctoNotFoundException extends RuntimeException {
    public DoctoNotFoundException(String message) {
        super(message);
    }
}
