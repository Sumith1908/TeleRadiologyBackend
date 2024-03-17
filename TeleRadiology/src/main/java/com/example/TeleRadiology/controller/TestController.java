package com.example.TeleRadiology.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping(value = "/test")
    public String test() throws Exception {

        // throw new Exception("This is a test exception");
        return "Hello, World!";
    }

    @GetMapping(value = "/secure")
    public String secure() {
        return "Secured text";
    }
}
