package com.example.TeleRadiology.domain.repositories;

import java.util.*;
import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.domain.model.Lab;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.model.Radiologist;
import com.example.TeleRadiology.dto.DocAndRadio;

public interface DetailsRepository {
    public Patient getPatient(int id);

    public Doctor getDoctor(int id);

    public Radiologist getRadiologist(int id);

    public Lab getLab(int id);

    public List<Doctor> getDoctors();

    public List<Radiologist> getRadiologists();

    public List<Patient> getPatients();

    public List<DocAndRadio> getAllDocAndRadio(int id);

    public List<Radiologist> getRadiologistsForReport(int id);
}