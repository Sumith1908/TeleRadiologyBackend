package com.example.TeleRadiology.domain.repositories;

import java.util.*;
import org.springframework.stereotype.Component;

import com.example.TeleRadiology.data.dao.DoctorDao;
import com.example.TeleRadiology.data.dao.LabDao;
import com.example.TeleRadiology.data.dao.PatientDao;
import com.example.TeleRadiology.data.dao.RadiologistDao;
import com.example.TeleRadiology.data.entities.DoctorEntity;
import com.example.TeleRadiology.data.entities.LabEntity;
import com.example.TeleRadiology.data.entities.PatientEntity;
import com.example.TeleRadiology.data.entities.RadiologistEntity;
import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.domain.model.Lab;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.model.Radiologist;
import com.example.TeleRadiology.exception.DoctoNotFoundException;
import com.example.TeleRadiology.exception.LabNotFoundException;
import com.example.TeleRadiology.exception.PatientNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DetailsRepositoryImplementation implements DetailsRepository {
    private final PatientDao patDao;
    private final DoctorDao docDao;
    private final RadiologistDao radDao;
    private final LabDao labDao;

    public Patient getPatient(int id) {
        PatientEntity patEnt = patDao.findByUserIdId(id).orElseThrow(
                () -> new PatientNotFoundException("No such patient"));
        return mapToDomainPatientEntity(patEnt);
    }

    public Doctor getDoctor(int id) {
        DoctorEntity docEnt = docDao.findByUserIdId(id).orElseThrow(
                () -> new PatientNotFoundException("No such doctor"));
        return mapToDomainDoctorEntity(docEnt);
    }

    public Radiologist getRadiologist(int id) {
        RadiologistEntity radEnt = radDao.findByUserIdId(id).orElseThrow(
                () -> new DoctoNotFoundException("No such radiologist"));
        return mapToDomainRadiologistEntity(radEnt);
    }

    public Lab getLab(int id) {
        LabEntity labEnt = labDao.findByUserIdId(id).orElseThrow(
                () -> new LabNotFoundException("No such lab"));
        return mapToDomainLabEntity(labEnt);
    }

    public List<Doctor> getDoctors(){
        List <DoctorEntity> doctorEntityList=new ArrayList<>();
        doctorEntityList=docDao.findAll();

        return mapAllToDomainDoctorEntity(doctorEntityList);
    }

    private List <Doctor> mapAllToDomainDoctorEntity(List <DoctorEntity> doctorEntityList){

        List <Doctor> doctorList=new ArrayList<>();

        for(DoctorEntity docEntity:doctorEntityList)
            doctorList.add(mapToDomainDoctorEntity(docEntity));

        return doctorList;
    }

    private Patient mapToDomainPatientEntity(PatientEntity patEnt) {
        Patient pat = new Patient();
        pat.setId(patEnt.getId());
        pat.setUserId(patEnt.getUserId().getId());
        pat.setFirstName(patEnt.getFirstName());
        pat.setMiddleName(patEnt.getMiddleName());
        pat.setLastName(patEnt.getLastName());
        pat.setDateOfBirth(patEnt.getDateOfBirth());
        pat.setGender(patEnt.getGender());
        pat.setAddress(patEnt.getAddress());
        pat.setCity(patEnt.getCity());
        pat.setState(patEnt.getState());
        pat.setPinCode(patEnt.getPinCode());
        pat.setEmail(patEnt.getEmail());
        pat.setPhoneNumber(patEnt.getPhoneNumber());
        pat.setEmergencyContact(patEnt.getEmergencyContact());
        pat.setBloodGroup(patEnt.getBloodGroup());
        pat.setHeight(patEnt.getHeight());
        pat.setWeight(patEnt.getWeight());
        pat.setProfilePhoto(patEnt.getProfilePhoto());

        return pat;
    }

    private Doctor mapToDomainDoctorEntity(DoctorEntity docEnt) {
        Doctor doc = new Doctor();
        doc.setId(docEnt.getId());
        doc.setUserId(docEnt.getUserId().getId());
        doc.setFirstName(docEnt.getFirstName());
        doc.setMiddleName(docEnt.getMiddleName());
        doc.setLastName(docEnt.getLastName());
        doc.setGender(docEnt.getGender());
        doc.setHospitalAddress(docEnt.getHospitalAddress());
        doc.setHospitalCity(docEnt.getHospitalCity());
        doc.setHospitalState(docEnt.getHospitalState());
        doc.setHospitalPinCode(docEnt.getHospitalPinCode());
        doc.setHospitalEmail(docEnt.getHospitalEmail());
        doc.setHospitalPhoneNumber(docEnt.getHospitalPhoneNumber());
        doc.setExperience(docEnt.getExperience());
        doc.setRating(docEnt.getRating());
        doc.setHighestEducation(docEnt.getHighestEducation());
        doc.setType(docEnt.getType());
        doc.setProfilePhoto(docEnt.getProfilePhoto());

        return doc;
    }

    private Radiologist mapToDomainRadiologistEntity(RadiologistEntity radEnt) {
        Radiologist rad = new Radiologist();
        rad.setId(radEnt.getId());
        rad.setUserId(radEnt.getUserId().getId());
        rad.setFirstName(radEnt.getFirstName());
        rad.setMiddleName(radEnt.getMiddleName());
        rad.setLastName(radEnt.getLastName());
        rad.setEmail(radEnt.getEmail());
        rad.setPhoneNumber(radEnt.getPhoneNumber());
        rad.setExperience(radEnt.getExperience());
        rad.setHighestEducation(radEnt.getHighestEducation());
        rad.setProfilePhoto(radEnt.getProfilePhoto());

        return rad;
    }

    private Lab mapToDomainLabEntity(LabEntity labEnt) {
        Lab lab = new Lab();
        lab.setId(labEnt.getId());
        lab.setName(labEnt.getName());
        lab.setAddress(labEnt.getAddress());
        lab.setCity(labEnt.getCity());
        lab.setState(labEnt.getState());
        lab.setPinCode(labEnt.getPinCode());
        lab.setUserId(labEnt.getUserId().getId());
        lab.setRating(labEnt.getRating());
        lab.setPhoneNumber(labEnt.getPhoneNumber());

        return lab;
    }
}