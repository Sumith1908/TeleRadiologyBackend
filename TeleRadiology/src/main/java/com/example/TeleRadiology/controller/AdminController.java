package com.example.TeleRadiology.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeleRadiology.domain.services.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AdminController {
     private final AdminService admin;
    @GetMapping(value = "/deactivateUser/{id}")
    public Boolean deactivateUser(@PathVariable int id )  {
        admin.deactivateUser(id);
        return true;
    }
}

