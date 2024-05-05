package com.example.TeleRadiology.domain.services;

import com.example.TeleRadiology.domain.model.Lab;
import com.example.TeleRadiology.domain.repositories.LabRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LabService {
    private final LabRepository labRepo;

    public void addLab(Lab lab, int id) {
        labRepo.addLab(lab, id);
    }
}
