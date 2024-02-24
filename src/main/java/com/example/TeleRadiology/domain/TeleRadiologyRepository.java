package com.example.TeleRadiology.domain;

import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.domain.model.Lab;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.model.Radiologist;

public interface TeleRadiologyRepository {
    public Credentials checkLoginCredentials(String email, int role);

    public Patient getPatient(int id);

    public Doctor getDoctor(int id);

    public Radiologist getRadiologist(int id);

    public Lab getLab(int id);

}
