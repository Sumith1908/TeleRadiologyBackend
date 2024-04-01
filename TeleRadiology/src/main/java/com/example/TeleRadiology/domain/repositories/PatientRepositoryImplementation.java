package com.example.TeleRadiology.domain.repositories;

import com.example.TeleRadiology.data.dao.CredentialsDao;
import com.example.TeleRadiology.data.dao.PatientDao;
import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.PatientEntity;
import com.example.TeleRadiology.domain.model.Patient;
import com.example.TeleRadiology.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatientRepositoryImplementation implements PatientRepository{
    private final PatientDao patDao;
    private final CredentialsDao credDao;

    @Override
    public void addPatient(Patient pat) {
        patDao.save(mapToEntityPatient(pat));
    }

    private PatientEntity mapToEntityPatient(Patient pat) {
        PatientEntity patEnt;
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
        patEnt.setPhoneNumber(pat.getPhoneNumber());
        patEnt.setEmergencyContact(pat.getEmergencyContact());
        patEnt.setBloodGroup(pat.getBloodGroup());
        patEnt.setHeight(pat.getHeight());
        patEnt.setWeight(pat.getWeight());
        patEnt.setProfilePhoto(pat.getProfilePhoto());
        patEnt.setAllergies(pat.getAllergies());
        patEnt.setCurrentMedication(pat.getCurrentMedication());
        patEnt.setPastMedication(pat.getPastMedication());
        patEnt.setChronicDiseases(pat.getChronicDiseases());
        patEnt.setSmokingHabits(pat.getSmokingHabits());
        patEnt.setDrinkingHabits(pat.getDrinkingHabits());
        patEnt.setFoodPreferences(pat.getFoodPreferences());
        return patEnt;    }
}
