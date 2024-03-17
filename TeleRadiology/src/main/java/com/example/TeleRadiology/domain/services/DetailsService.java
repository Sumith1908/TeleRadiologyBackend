package com.example.TeleRadiology.domain.services;

import org.springframework.stereotype.Service;

import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.domain.model.Lab;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.model.Radiologist;
import com.example.TeleRadiology.domain.repositories.DetailsRepository;
import com.example.TeleRadiology.dto.DoctorResult;
import com.example.TeleRadiology.dto.LabResult;
import com.example.TeleRadiology.dto.PatientResult;
import com.example.TeleRadiology.dto.RadiologistResult;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetailsService {
    private final DetailsRepository detRep;

    public PatientResult getPatient(int id) {
        Patient pat = detRep.getPatient(id);
        return mapToDtoPatient(pat);
    }

    public DoctorResult getDoctor(int id) {
        Doctor doc = detRep.getDoctor(id);
        return mapToDtoDoctor(doc);
    }

    public RadiologistResult getRadiologist(int id) {
        Radiologist rad = detRep.getRadiologist(id);
        return mapToDtoRadiologist(rad);
    }

    public LabResult getLab(int id) {
        Lab lab = detRep.getLab(id);
        return mapToDtoLabResult(lab);
    }

    private DoctorResult mapToDtoDoctor(Doctor doc) {
        DoctorResult docRes = new DoctorResult();
        docRes.setId(doc.getId());
        docRes.setUserId(doc.getUserId());
        docRes.setFirstName(doc.getFirstName());
        docRes.setMiddleName(doc.getMiddleName());
        docRes.setLastName(doc.getLastName());
        docRes.setGender(doc.getGender());
        docRes.setHospitalAddress(doc.getHospitalAddress());
        docRes.setHospitalCity(doc.getHospitalCity());
        docRes.setHospitalState(doc.getHospitalState());
        docRes.setHospitalPinCode(doc.getHospitalPinCode());
        docRes.setHospitalEmail(doc.getHospitalEmail());
        docRes.setHospitalPhoneNumber(doc.getHospitalPhoneNumber());
        docRes.setExperience(doc.getExperience());
        docRes.setRating(doc.getRating());
        docRes.setHighestEducation(doc.getHighestEducation());
        docRes.setType(doc.getType());
        docRes.setProfilePhoto(doc.getProfilePhoto());

        return docRes;
    }

    private PatientResult mapToDtoPatient(Patient pat) {
        PatientResult patRes = new PatientResult();

        patRes.setId(pat.getId());
        patRes.setUserId(pat.getUserId());
        patRes.setFirstName(pat.getFirstName());
        patRes.setMiddleName(pat.getMiddleName());
        patRes.setLastName(pat.getLastName());
        patRes.setDateOfBirth(pat.getDateOfBirth());
        patRes.setGender(pat.getGender());
        patRes.setAddress(pat.getAddress());
        patRes.setCity(pat.getCity());
        patRes.setState(pat.getState());
        patRes.setPinCode(pat.getPinCode());
        patRes.setEmail(pat.getEmail());
        patRes.setPhoneNumber(pat.getPhoneNumber());
        patRes.setEmergencyContact(pat.getEmergencyContact());
        patRes.setBloodGroup(pat.getBloodGroup());
        patRes.setHeight(pat.getHeight());
        patRes.setWeight(pat.getWeight());
        patRes.setProfilePhoto(pat.getProfilePhoto());
        return patRes;
    }

    private RadiologistResult mapToDtoRadiologist(Radiologist rad) {
        RadiologistResult radRes = new RadiologistResult();
        radRes.setId(rad.getId());
        radRes.setUserId(rad.getUserId());
        radRes.setFirstName(rad.getFirstName());
        radRes.setMiddleName(rad.getMiddleName());
        radRes.setLastName(rad.getLastName());
        radRes.setEmail(rad.getEmail());
        radRes.setPhoneNumber(rad.getPhoneNumber());
        radRes.setExperience(rad.getExperience());
        radRes.setHighestEducation(rad.getHighestEducation());
        radRes.setProfilePhoto(rad.getProfilePhoto());

        return radRes;
    }

    private LabResult mapToDtoLabResult(Lab lab) {
        LabResult labRes = new LabResult();
        labRes.setId(lab.getId());
        labRes.setName(lab.getName());
        labRes.setAddress(lab.getAddress());
        labRes.setCity(lab.getCity());
        labRes.setState(lab.getState());
        labRes.setPinCode(lab.getPinCode());
        labRes.setUserId(lab.getUserId());
        labRes.setRating(lab.getRating());
        labRes.setPhoneNumber(lab.getPhoneNumber());

        return labRes;
    }

}