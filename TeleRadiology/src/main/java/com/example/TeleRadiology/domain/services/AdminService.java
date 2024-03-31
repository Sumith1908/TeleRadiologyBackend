package com.example.TeleRadiology.domain.services;

import org.springframework.stereotype.Service;

import com.example.TeleRadiology.domain.repositories.AdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepo;
    public boolean deactivateUser(int id)
    {
        adminRepo.deactivateUser(id);
        return true;
    }
    
}
