package com.example.TeleRadiology.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.TeleRadiology.dto.*;
import org.springframework.web.bind.annotation.*;

import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.model.Radiologist;
import com.example.TeleRadiology.domain.services.DetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teleRadiology")
@RequiredArgsConstructor
// @CrossOrigin(originPatterns = "*localhost*")
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
        List<Doctor> doctorList = new ArrayList<>();
        doctorList = detService.getListOfDoctors();

        return doctorList;
    }

    @GetMapping("/getAllRadiologists")
    public List<Radiologist> getAllRadiologists() {
        List<Radiologist> radiologistList = new ArrayList<>();
        radiologistList = detService.getListOfRadiologists();

        return radiologistList;
    }

    @PostMapping("/getAllPatients")
    public List<Patient> getAllPatients() {
        List<Patient> patientList = new ArrayList<>();
        patientList = detService.getListOfPatients();

        return patientList;
    }

    @GetMapping("getAllDoctorsAndRadiologits/{id}")
    public List<DocAndRadio> getAllDocAndRadiocheckEmail(@PathVariable("id") int id) {
        List<DocAndRadio> docAndRadioList=new ArrayList<>();
        docAndRadioList=detService.getAllDocAndRadio(id);
        return docAndRadioList;
    }

    @GetMapping("getRadiologists/{id}")
    public List<Radiologist> getRadiologists(@PathVariable("id") int id) {
        List<Radiologist> radiologistList=new ArrayList<>();
        radiologistList=detService.getRadiologists(id);
        return  radiologistList;
    }
}