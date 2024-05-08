package com.example.TeleRadiology.domain.services;

import java.util.*;

import com.example.TeleRadiology.data.dao.NotificationDao;
import com.example.TeleRadiology.data.entities.NotificationEntity;
import com.example.TeleRadiology.dto.*;
import org.springframework.stereotype.Service;

import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.domain.model.Lab;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.model.Radiologist;
import com.example.TeleRadiology.domain.repositories.DetailsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DetailsService {
    private final DetailsRepository detRep;
    private final NotificationDao notificationDao;
    private final ImageService imgService;

    public PatientResult getPatient(int id) {
        Patient pat = detRep.getPatient(id);
        PatientResult patRes = mapToDtoPatient(pat);
        // commented untill required
         ProfilePicDTO dto = imgService.callImageServerGet("/getProfilePic/" +
         Integer.toString(pat.getUserId()),
         ProfilePicDTO.class);
         patRes.setProfilePhoto(dto.getProfilePic());
        return patRes;
    }

    public DoctorResult getDoctor(int id) {
        Doctor doc = detRep.getDoctor(id);
        DoctorResult docRes = mapToDtoDoctor(doc);
//         ProfilePicDTO dto = imgService.callImageServerGet("/getProfilePic/" +
//         Integer.toString(doc.getUserId()),
//         ProfilePicDTO.class);
//         docRes.setProfilePhoto(dto.getProfilePic());
        return docRes;
    }

    public RadiologistResult getRadiologist(int id) {
        Radiologist rad = detRep.getRadiologist(id);
        RadiologistResult radRes = mapToDtoRadiologist(rad);
//         ProfilePicDTO dto = imgService.callImageServerGet("/getProfilePic/" +
//         Integer.toString(rad.getUserId()),
//         ProfilePicDTO.class);
//         radRes.setProfilePhoto(dto.getProfilePic());
        return radRes;
    }

    public LabResult getLab(int id) {
        Lab lab = detRep.getLab(id);
        return mapToDtoLabResult(lab);
    }

    public List<Doctor> getListOfDoctors() {
        List<Doctor> doctorList = new ArrayList<>();
        doctorList = detRep.getDoctors();
        return doctorList;
    }

    public List<Radiologist> getListOfRadiologists() {
        List<Radiologist> radiologistList = new ArrayList<>();
        radiologistList = detRep.getRadiologists();
        return radiologistList;
    }

    public List<Patient> getListOfPatients() {
        List<Patient> patientList = new ArrayList<>();
        patientList = detRep.getPatients();
        return patientList;
    }

    public List<DocAndRadio> getAllDocAndRadio(int id) {
        List<DocAndRadio> docAndRadioList = new ArrayList<>();
        docAndRadioList = detRep.getAllDocAndRadio(id);
        return docAndRadioList;
    }

    public List<Radiologist> getRadiologists(int id) {
        List<Radiologist> radiologistList = new ArrayList<>();
        radiologistList = detRep.getRadiologistsForReport(id);
        return radiologistList;
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
        patRes.setAllergies(pat.getAllergies());
        patRes.setChronicDiseases(pat.getChronicDiseases());
        patRes.setCurrentMedication(pat.getCurrentMedication());
        patRes.setPastMedication(pat.getPastMedication());
        patRes.setSmokingHabits(pat.getSmokingHabits());
        patRes.setDrinkingHabits(pat.getDrinkingHabits());
        patRes.setFoodPreferences(pat.getFoodPreferences());

        NotificationEntity notificationEntity=new NotificationEntity();
        notificationEntity=notificationDao.findByPatientIdId(pat.getId()).orElse(null);
        if(notificationEntity!=null) {
            patRes.setNotification(1);
        }

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