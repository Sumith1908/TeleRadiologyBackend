package com.example.TeleRadiology.domain.repositories;

import java.util.*;

import com.example.TeleRadiology.data.dao.*;
import com.example.TeleRadiology.data.entities.*;
import com.example.TeleRadiology.domain.services.AesService;
import com.example.TeleRadiology.dto.DocAndRadio;
import com.example.TeleRadiology.dto.RadiologistResult;
import com.example.TeleRadiology.exception.GlobalException;
import org.springframework.stereotype.Component;

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
    private final ConsentDao consentDao;
    private final AesService aesService;
    private final NotificationDao notificationDao;

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

    public List<Doctor> getDoctors() {
        List<DoctorEntity> doctorEntityList = new ArrayList<>();
        doctorEntityList = docDao.findAll();

        return mapAllToDomainDoctorEntity(doctorEntityList);
    }

    public List<Radiologist> getRadiologists() {
        List<RadiologistEntity> radiologistEntityList = new ArrayList<>();
        radiologistEntityList = radDao.findAll();

        return mapAllToDomainRadiologistEntity(radiologistEntityList);
    }

    public List<Patient> getPatients() {
        List<PatientEntity> patientEntityList = new ArrayList<>();
        patientEntityList = patDao.findAll();

        return mapAllToDomainPatientEntity(patientEntityList);
    }

    public List<DocAndRadio> getAllDocAndRadio(int reportId) {
        List<DocAndRadio> docAndRadioList = new ArrayList<>();

        findDoctors(reportId, docAndRadioList);
        findRadiologists(reportId, docAndRadioList);

        Collections.sort(docAndRadioList, (a, b) -> Integer.compare(b.getConsent(), a.getConsent()));

        return docAndRadioList;
    }

    public List<Radiologist> getRadiologistsForReport(int reportId) {
        List<RadiologistEntity> radiologistEntityList=new ArrayList<>();
        List<Radiologist> radiologistList=new ArrayList<>();
        radiologistEntityList=radDao.findAll();

        for(RadiologistEntity radiologistEntity:radiologistEntityList) {
            Radiologist radiologist=new Radiologist();
            radiologist=mapRadEntToRadDto(radiologistEntity, reportId);
            radiologistList.add(radiologist);
        }

         return radiologistList;
    }

    private Radiologist mapRadEntToRadDto(RadiologistEntity radEnt, int reportId) {
        ConsentEntity consentEntity=new ConsentEntity();
        NotificationEntity notificationEntity=new NotificationEntity();

        Radiologist rad = new Radiologist();

        consentEntity = consentDao.findByViewerIdIdAndReportIdId(radEnt.getUserId().getId(), reportId).orElse(null);
        notificationEntity = notificationDao.findByRadiologistIdIdAndReportIdId(radEnt.getId(), reportId).orElse(null);

        if(consentEntity!=null) {
            rad.setConsent(1);
        }

        if(notificationEntity!=null) {
            rad.setConsent(2);
        }

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


    private void findDoctors(int reportId, List<DocAndRadio> docAndRadioList) {

        List<DoctorEntity> doctorEntityList=new ArrayList<>();
        doctorEntityList=docDao.findAll();

        for(DoctorEntity docEnt:doctorEntityList) {
            ConsentEntity consentEntity=new ConsentEntity();
            consentEntity=consentDao.findByViewerIdIdAndReportIdId(docEnt.getUserId().getId(), reportId).orElse(null);

            String name="";
            if(docEnt.getMiddleName()==null) {
                name = docEnt.getFirstName()+" "+docEnt.getLastName();
            }
            else {
                name = docEnt.getFirstName()+" "+docEnt.getMiddleName()+" "+docEnt.getLastName();
            }

            DocAndRadio docAndRadio=new DocAndRadio();

            docAndRadio.setName(name);
            docAndRadio.setId(docEnt.getId());
            docAndRadio.setCredId(docEnt.getUserId().getId());
            docAndRadio.setRole("Doctor");
            if(consentEntity!=null) {
                docAndRadio.setConsent(1);
            }

             docAndRadioList.add(docAndRadio);
        }
    }

    private void findRadiologists(int reportId, List<DocAndRadio> docAndRadioList) {
        List<RadiologistEntity> radiologistEntityList=new ArrayList<>();
        radiologistEntityList=radDao.findAll();
        for(RadiologistEntity radiologistEntity:radiologistEntityList) {
            ConsentEntity consentEntity=new ConsentEntity();
            consentEntity=consentDao.findByViewerIdIdAndReportIdId(radiologistEntity.getUserId().getId(), reportId).orElse(null);

            String name="";
            if(radiologistEntity.getMiddleName()==null) {
                name = radiologistEntity.getFirstName()+" "+radiologistEntity.getLastName();
            }
            else {
                name = radiologistEntity.getFirstName()+" "+radiologistEntity.getMiddleName()+" "+radiologistEntity.getLastName();
            }

            DocAndRadio docAndRadio=new DocAndRadio();

            docAndRadio.setName(name);
            docAndRadio.setId(radiologistEntity.getId());
            docAndRadio.setCredId(radiologistEntity.getUserId().getId());
            docAndRadio.setRole("Radiologist");
            if(consentEntity!=null) {
                docAndRadio.setConsent(1);
            }

             docAndRadioList.add(docAndRadio);
        }
    }

    private List<Doctor> mapAllToDomainDoctorEntity(List<DoctorEntity> doctorEntityList) {

        List<Doctor> doctorList = new ArrayList<>();

        for (DoctorEntity docEntity : doctorEntityList) {
            doctorList.add(mapToDomainDoctorEntity(docEntity));
        }
         return doctorList;
    }

    private List<Radiologist> mapAllToDomainRadiologistEntity(List<RadiologistEntity> radiologistEntityList) {
        List<Radiologist> radaiologistList = new ArrayList<>();

        for(RadiologistEntity radEntity : radiologistEntityList) {
            radaiologistList.add(mapToDomainRadiologistEntity(radEntity));
        }
         return radaiologistList;
    }

    private List<Patient> mapAllToDomainPatientEntity(List<PatientEntity> patientEntityList) {

        List<Patient> patientList = new ArrayList<>();

        for (PatientEntity patEntity : patientEntityList) {
            patientList.add(mapToDomainPatientEntity(patEntity));

        }
        return patientList;
    }

    private Patient mapToDomainPatientEntity(PatientEntity patEnt) {
        Patient pat = new Patient();
        try {
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
            pat.setPhoneNumber(aesService.decrypt(patEnt.getPhoneNumber()));
            pat.setEmergencyContact(aesService.decrypt(patEnt.getEmergencyContact()));
            pat.setBloodGroup(patEnt.getBloodGroup());
            pat.setHeight(patEnt.getHeight());
            pat.setWeight(patEnt.getWeight());
            pat.setProfilePhoto(patEnt.getProfilePhoto());
            pat.setAllergies(aesService.decrypt(patEnt.getAllergies()));
            pat.setChronicDiseases(aesService.decrypt(patEnt.getChronicDiseases()));
            pat.setCurrentMedication(aesService.decrypt(patEnt.getCurrentMedication()));
            pat.setPastMedication(aesService.decrypt(patEnt.getPastMedication()));
            pat.setSmokingHabits(aesService.decrypt(patEnt.getSmokingHabits()));
            pat.setDrinkingHabits(aesService.decrypt(patEnt.getDrinkingHabits()));
            pat.setFoodPreferences(aesService.decrypt(patEnt.getFoodPreferences()));
        }
        catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }

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
        ConsentEntity consentEntity=new ConsentEntity();
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