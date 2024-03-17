package com.example.TeleRadiology.domain.services;

import org.springframework.stereotype.Service;

import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.domain.repositories.TeleRadiologyRepository;
import com.example.TeleRadiology.dto.CredentialsRequest;
import com.example.TeleRadiology.dto.CredentialsResult;
import com.example.TeleRadiology.exception.WrongPasswordException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeleRadiologyService {
    private final TeleRadiologyRepository teleRep;

    public CredentialsResult checkCredentials(String email, int role, String password) {
        Credentials cred = teleRep.checkLoginCredentials(email, role);
        CredentialsResult credRes = new CredentialsResult();
        credRes.setUser(cred.getId());
        if (!cred.getPassword().equals(password)) {
            credRes.setPassword(0);
            throw new WrongPasswordException("wrong password");
        } else {
            credRes.setPassword(1);
        }
        return credRes;
    }

    public CredentialsResult addPatient(CredentialsRequest cred) {
        CredentialsResult credRes = new CredentialsResult();
        credRes.setUser(teleRep.addPatient(cred));
        credRes.setPassword(1);
        return credRes;
    }

}
