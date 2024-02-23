package com.example.TeleRadiology.domain;

import com.example.TeleRadiology.domain.model.Credentials;

public interface TeleRadiologyRepository {
    public Credentials checkLoginCredentials(String email, int role);
}
