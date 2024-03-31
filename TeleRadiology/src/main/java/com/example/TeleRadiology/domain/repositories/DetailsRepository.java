package com.example.TeleRadiology.domain.repositories;

import java.util.*;
import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.domain.model.Lab;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.model.Radiologist;

public interface DetailsRepository {
    public Patient getPatient(int id);

    public Doctor getDoctor(int id);

    public Radiologist getRadiologist(int id);

    public Lab getLab(int id);

    public List<Doctor> getDoctors();


    public List<Patient> getPatients();
}