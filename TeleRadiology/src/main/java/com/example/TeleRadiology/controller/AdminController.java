package com.example.TeleRadiology.controller;

import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.domain.model.Lab;
import com.example.TeleRadiology.domain.model.Radiologist;
import com.example.TeleRadiology.domain.services.*;
import com.example.TeleRadiology.dto.*;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teleRadiology")
// @CrossOrigin(originPatterns = "*localhost*")
public class AdminController {
    private final AdminService admin;
    private final TeleRadiologyService teleRadService;
    private final DoctorService doctorService;
    private final RadiologistService radioService;
    private final LabService labService;

    @GetMapping(value = "/deactivateUser/{id}")
    public Boolean deactivateUser(@PathVariable int id) {
        admin.deactivateUser(id);
        return true;
    }

    @PostMapping(value = "/addDoctor")
    public Boolean addDoctor(@RequestBody AddDoctorReq doc) {
        CredentialsRequest credReq = new CredentialsRequest();
        credReq = mapDocReqToCredReq(doc);
        CredentialsResult credRes = teleRadService.addPatient(credReq);
        doctorService.addDoctor(mapReqToDoctor(doc));
        return true;
    }

    private CredentialsRequest mapDocReqToCredReq(@RequestBody AddDoctorReq doc) {
        CredentialsRequest credReq = new CredentialsRequest();
        credReq.setEmail(doc.getHospitalEmail());
        credReq.setPassword(doc.getPassword());
        credReq.setRole(doc.getRole());
        return credReq;
    }

    private Doctor mapReqToDoctor(@RequestBody AddDoctorReq doc) {
        Doctor res = new Doctor();
        res.setFirstName(doc.getFirstName());
        res.setMiddleName(doc.getMiddleName());
        res.setLastName(doc.getLastName());
        res.setGender(doc.getGender());
        res.setHospitalAddress(doc.getHospitalAddress());
        res.setHospitalCity(doc.getHospitalCity());
        res.setHospitalState(doc.getHospitalState());
        res.setHospitalPinCode(doc.getHospitalPinCode());
        res.setHospitalEmail(doc.getHospitalEmail());
        res.setHospitalPhoneNumber(doc.getHospitalPhoneNumber());
        res.setExperience(doc.getExperience());
        res.setHighestEducation(doc.getHighestEducation());
        res.setType(doc.getType());
        res.setProfilePhoto(doc.getProfilePhoto());
        return res;
    }

    @PostMapping(value = "/addRadiologist")
    public Boolean addRadiologist(@RequestBody AddRadiologistReq radio) {
        CredentialsRequest credReq = mapRadioReqToCredReq(radio);
        CredentialsResult credRes = teleRadService.addPatient(credReq);
        radioService.addRadiologist(mapReqToRadiologist(radio));
        return true;
    }

    private CredentialsRequest mapRadioReqToCredReq(@RequestBody AddRadiologistReq radio) {
        CredentialsRequest credReq = new CredentialsRequest();
        credReq.setEmail(radio.getEmail());
        credReq.setPassword(radio.getPassword());
        credReq.setRole(radio.getRole());
        return credReq;
    }

    private Radiologist mapReqToRadiologist(@RequestBody AddRadiologistReq radio) {
        Radiologist res = new Radiologist();
        res.setFirstName(radio.getFirstName());
        res.setMiddleName(radio.getMiddleName());
        res.setLastName(radio.getLastName());
        res.setEmail(radio.getEmail());
        res.setPhoneNumber(radio.getPhoneNumber());
        res.setExperience(radio.getExperience());
        res.setHighestEducation(radio.getHighestEducation());
        res.setProfilePhoto(radio.getProfilePhoto());
        return res;
    }

    @PostMapping(value = "/addLab")
    public Boolean addLab(@RequestBody AddLabReq lab) {
        CredentialsRequest credReq = new CredentialsRequest();
        credReq = mapLabReqToCredReq(lab);
        CredentialsResult credRes = teleRadService.addPatient(credReq);
        labService.addLab(mapReqToLab(lab), credRes.getUser());
        return true;
    }

    private CredentialsRequest mapLabReqToCredReq(@RequestBody AddLabReq lab) {
        CredentialsRequest credReq = new CredentialsRequest();
        credReq.setEmail(lab.getEmail());
        credReq.setPassword(lab.getPassword());
        credReq.setRole(lab.getRole());
        return credReq;
    }

    private Lab mapReqToLab(@RequestBody AddLabReq lab) {
        Lab res = new Lab();
        res.setName(lab.getName());
        res.setAddress(lab.getAddress());
        res.setCity(lab.getCity());
        res.setState(lab.getState());
        res.setPinCode(lab.getPinCode());
        res.setPhoneNumber(lab.getPhoneNumber());
        return res;
    }
}
