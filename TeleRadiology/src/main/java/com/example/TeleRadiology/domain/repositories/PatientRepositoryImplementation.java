package com.example.TeleRadiology.domain.repositories;

import com.example.TeleRadiology.data.dao.CredentialsDao;
import com.example.TeleRadiology.data.dao.PatientDao;
import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.PatientEntity;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.domain.services.AesService;
import com.example.TeleRadiology.exception.GlobalException;
import com.example.TeleRadiology.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientRepositoryImplementation implements PatientRepository{
    private final PatientDao patDao;
    private final CredentialsDao credDao;
    private final AesService aesService;

    @Override
    public int addPatient(Patient pat) {
        PatientEntity patientEntity=new PatientEntity();
        patientEntity=patDao.save(mapToEntityPatient(pat));
        return patientEntity.getUserId().getId();
    }

    private PatientEntity mapToEntityPatient(Patient pat) {
        PatientEntity patEnt;
        try {
            CredentialsEntity credEnt = credDao.findById(pat.getUserId()).orElseThrow(
                    () -> new UserNotFoundException("No Such User")
            );
            patEnt = patDao.findByUserIdId(pat.getUserId()).orElse(null);
            if (patEnt == null) {
                patEnt = new PatientEntity();
            }
            patEnt.setUserId(credEnt);
            patEnt.setFirstName(pat.getFirstName());
            patEnt.setMiddleName(pat.getMiddleName());
            patEnt.setLastName(pat.getLastName());
            patEnt.setDateOfBirth(pat.getDateOfBirth());
            patEnt.setGender(pat.getGender());
            patEnt.setAddress(pat.getAddress());
            patEnt.setCity(pat.getCity());
            patEnt.setState(pat.getState());
            patEnt.setPinCode(pat.getPinCode());
            patEnt.setEmail(pat.getEmail());
            patEnt.setPhoneNumber(aesService.encrypt(pat.getPhoneNumber()));
            patEnt.setEmergencyContact(aesService.encrypt(pat.getEmergencyContact()));
            patEnt.setBloodGroup(pat.getBloodGroup());
            patEnt.setHeight(pat.getHeight());
            patEnt.setWeight(pat.getWeight());
            patEnt.setProfilePhoto(pat.getProfilePhoto());
            patEnt.setAllergies(aesService.encrypt(pat.getAllergies()));
            patEnt.setCurrentMedication(aesService.encrypt(pat.getCurrentMedication()));
            patEnt.setPastMedication(aesService.encrypt(pat.getPastMedication()));
            patEnt.setChronicDiseases(aesService.encrypt(pat.getChronicDiseases()));
            patEnt.setSmokingHabits(aesService.encrypt(pat.getSmokingHabits()));
            patEnt.setDrinkingHabits(aesService.encrypt(pat.getDrinkingHabits()));
            patEnt.setFoodPreferences(aesService.encrypt(pat.getFoodPreferences()));
        }
        catch (Exception e) {
            throw new GlobalException(e.getMessage());
        }
         return patEnt;
    }
}