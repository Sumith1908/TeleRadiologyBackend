package com.example.TeleRadiology.domain.services;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.domain.repositories.TeleRadiologyRepository;
import com.example.TeleRadiology.dto.CredentialsRequest;
import com.example.TeleRadiology.dto.CredentialsResult;
import com.example.TeleRadiology.exception.WrongPasswordException;
import java.security.MessageDigest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeleRadiologyService {
    private final TeleRadiologyRepository teleRep;

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CredentialsResult checkCredentials(String email, String role, String password) {
        Credentials cred = teleRep.checkLoginCredentials(email, role);
        CredentialsResult credRes = new CredentialsResult();
        credRes.setUser(cred.getId());
        if (!cred.getPassword().equals(hashPassword(password))) {
            credRes.setPassword(0);
            throw new WrongPasswordException("wrong password");
        } else {
            credRes.setPassword(1);
        }
        return credRes;
    }

    public CredentialsResult addPatient(CredentialsRequest cred) {
        CredentialsResult credRes = new CredentialsResult();
        cred.setPassword(hashPassword(cred.getPassword()));
        credRes.setUser(teleRep.addPatient(cred));
        credRes.setPassword(1);
        return credRes;
    }

    public Credentials getDoctorByEmail(String email) {
        return teleRep.getUserByEmail(email);
    }

}
