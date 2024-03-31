package com.example.TeleRadiology.controller;

import java.util.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TeleRadiology.domain.services.DetailsService;
import com.example.TeleRadiology.dto.DetailsRequest;
import com.example.TeleRadiology.dto.DoctorResult;
import com.example.TeleRadiology.dto.LabResult;
import com.example.TeleRadiology.dto.PatientResult;
import com.example.TeleRadiology.dto.RadiologistResult;
import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.domain.model.Patient;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teleRadiology")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*localhost*")
public class DetailsController {
    private final DetailsService detService;

    @PostMapping("/getPatient")
    public PatientResult getPatient(@RequestBody DetailsRequest detReq) {
        PatientResult pat = detService.getPatient(detReq.getId());
        return pat;
    }

    @PostMapping("/getDoctor")
    public DoctorResult getDoctor(@RequestBody DetailsRequest detReq) {
        DoctorResult doc = detService.getDoctor(detReq.getId());
        return doc;
    }

    @PostMapping("/getRadiologist")
    public RadiologistResult getRadiologist(@RequestBody DetailsRequest detReq) {
        RadiologistResult rad = detService.getRadiologist(detReq.getId());
        return rad;
    }

    @PostMapping("/getLab")
    public LabResult getLab(@RequestBody DetailsRequest detReq) {
        LabResult lab = detService.getLab(detReq.getId());
        return lab;
    }

    @GetMapping("/getAllDoctors")
    public List<Doctor> getAllDoctors() {
        List <Doctor> doctorList=new ArrayList<>();
        doctorList=detService.getListOfDoctors();

        return doctorList;
    }
    @GetMapping("/getAllPatients")
    public List<Patient> getAllPatients() {
        List <Patient> patientList=new ArrayList<>();
        patientList=detService.getListOfPatients();

        return patientList;
    }
}