package com.example.TeleRadiology.domain.services;

import com.example.TeleRadiology.domain.model.Radiologist;
import com.example.TeleRadiology.domain.repositories.RadiologistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RadiologistService {
    private final RadiologistRepository radioRepo;

    public void addRadiologist(Radiologist radio) {
        radioRepo.addRadiologist(radio);
    }
}
