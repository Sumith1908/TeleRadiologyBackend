package com.example.TeleRadiology.controller;

import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.services.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patService;

    @PostMapping("/addPatient")
    public Boolean getPatient(@RequestBody Patient pat) {
        patService.addPatient(pat);
        return true;
    }
}
