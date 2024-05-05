package com.example.TeleRadiology.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teleRadiology")
// @CrossOrigin(originPatterns = "*localhost*")
public class AuthenticationController {

    @GetMapping(value = "/authenticate")
    public Boolean test() throws Exception {
        return true;
    }
}