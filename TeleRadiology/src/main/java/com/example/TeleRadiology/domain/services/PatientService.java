package com.example.TeleRadiology.domain.services;

import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PatientService {
    private final PatientRepository patRepo;

    public void addPatient (Patient pat) {
        patRepo.addPatient(pat);
    }
}
