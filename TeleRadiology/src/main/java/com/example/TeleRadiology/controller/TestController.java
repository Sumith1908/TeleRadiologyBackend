package com.example.TeleRadiology.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/test")
    public Boolean test() throws Exception {
        return true;
    }

    @GetMapping(value = "/secure")
    public String secure() {
        return "Secured text";
    }
}
