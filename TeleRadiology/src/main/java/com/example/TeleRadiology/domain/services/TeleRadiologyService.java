package com.example.TeleRadiology.domain.services;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.example.TeleRadiology.data.dao.SaltDao;
import com.example.TeleRadiology.data.entities.SaltEntity;
import com.example.TeleRadiology.dto.ChangePasswordReq;
import com.example.TeleRadiology.exception.UserNotFoundException;
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
    private final SaltDao saltDao;

    private String generateSalt() {
        int length = 16;
        String saltChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

        SecureRandom random = new SecureRandom();

        byte[] saltBytes = new byte[length];

        random.nextBytes(saltBytes);

        StringBuilder salt = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = Math.abs(random.nextInt()) % saltChars.length();
            salt.append(saltChars.charAt(index));
        }

        return salt.toString();
    }

    private String hashPassword(String password) {
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
        String salt = teleRep.getSalt(cred.getId());
        credRes.setUser(cred.getId());
        if (!cred.getPassword().equals(hashPassword(password + salt))) {
            credRes.setPassword(0);
            throw new WrongPasswordException("wrong password");
        } else {
            credRes.setPassword(1);
        }
        return credRes;
    }

    public CredentialsResult addPatient(CredentialsRequest cred) {
        CredentialsResult credRes = new CredentialsResult();
        String salt = generateSalt();
        cred.setPassword(hashPassword(cred.getPassword() + salt));
        credRes.setUser(teleRep.addPatient(cred));
        credRes.setPassword(1);
        teleRep.addSalt(credRes.getUser(), salt);
        return credRes;
    }

    public Credentials checkEmail(String emailId) {
        Credentials res=new Credentials();
        res=teleRep.getUserByEmail(emailId);

        return res;
    }

    public void changePassword(ChangePasswordReq changePasswordReq) {
        SaltEntity saltEntity = new SaltEntity();
        saltEntity=saltDao.findByUserIdId(changePasswordReq.getId()).orElseThrow(() -> new UserNotFoundException("No such User"));
        String salt=saltEntity.getSalt();

        String hashedPassword = hashPassword(changePasswordReq.getPassword()+salt);
        changePasswordReq.setPassword(hashedPassword);

        teleRep.changePassword(changePasswordReq);
    }
    public Credentials getDoctorByEmail(String email) {
        return teleRep.getUserByEmail(email);
    }

    public void deleteToken(String token) {
        teleRep.deleteToken(token);
    }
}