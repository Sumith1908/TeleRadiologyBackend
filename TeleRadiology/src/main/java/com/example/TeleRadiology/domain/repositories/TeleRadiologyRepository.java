package com.example.TeleRadiology.domain.repositories;

import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.dto.CredentialsRequest;

public interface TeleRadiologyRepository {
    public Credentials checkLoginCredentials(String email, int role);

    public int addPatient(CredentialsRequest cred);

    public Credentials getUserByEmail(String email);
}
