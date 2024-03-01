package com.example.TeleRadiology.exception;

public class ConsentNotFoundException extends RuntimeException{
    public ConsentNotFoundException(String message)
    {
        super(message);
    }
}
