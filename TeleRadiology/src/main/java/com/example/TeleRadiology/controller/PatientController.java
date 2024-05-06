package com.example.TeleRadiology.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.services.AesService;
import com.example.TeleRadiology.domain.services.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teleRadiology")
// @CrossOrigin(origins = "*http://localhost:3000*")
public class PatientController {
    private final PatientService patService;
    private final AesService aes;

    @PostMapping("/addPatient")
    public Boolean getPatient(@RequestBody Patient pat) {
        patService.addPatient(pat);
        return true;
    }

    @GetMapping("/test")
    public String testGet() throws Exception {
        return aes.decrypt("iFYkB452yXNqoe0WE6SzwQ==");
    }

    @PostMapping("/test")
    public String testPost() {

        return "Post Test";
    }
}
