package com.example.TeleRadiology.domain.repositories;

import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.dto.ChangePasswordReq;
import com.example.TeleRadiology.dto.CredentialsRequest;

public interface TeleRadiologyRepository {
    public Credentials checkLoginCredentials(String email, String role);

    public int addPatient(CredentialsRequest cred);

    public Credentials getUserByEmail(String email);

    public void changePassword(ChangePasswordReq changePasswordReq);

    public void addSalt(int id, String salt);

    public String getSalt(int id);

    public void deleteToken(String token);
}