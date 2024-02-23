package com.example.TeleRadiology.domain;

import org.springframework.stereotype.Service;

import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.exception.WrongPasswordException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeleRadiologyService {
    private final TeleRadiologyRepository teleRep;

    public int checkCredentials(String email, int role, String password) {
        Credentials cred = teleRep.checkLoginCredentials(email, role);
        if (!cred.getPassword().equals(password)) {
            throw new WrongPasswordException("Wrong Password");
        }
        return cred.getId();
    }
}
