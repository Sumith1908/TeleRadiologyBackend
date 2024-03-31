package com.example.TeleRadiology.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @GetMapping(value = "/authenticate")
    public Boolean test() throws Exception {
        return true;
    }
}
