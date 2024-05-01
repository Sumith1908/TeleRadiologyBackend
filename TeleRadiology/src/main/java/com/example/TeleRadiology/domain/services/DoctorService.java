package com.example.TeleRadiology.domain.services;

import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.domain.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DoctorService {
    private final DoctorRepository docRepo;
    public void addDoctor(Doctor doc) {
        docRepo.addDoctor(doc);
    }
}
