package com.example.TeleRadiology.domain.repositories;

import com.example.TeleRadiology.data.dao.CredentialsDao;
import com.example.TeleRadiology.data.dao.RadiologistDao;
import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.RadiologistEntity;
import com.example.TeleRadiology.domain.model.Radiologist;
import com.example.TeleRadiology.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RadiologistRepositoryImplementation implements RadiologistRepository{
    private final CredentialsDao credDao;
    private final RadiologistDao radioDao;

    @Override
    public void addRadiologist(Radiologist radio) {
        radioDao.save(mapToRadiologistEntity(radio));
    }

    private RadiologistEntity mapToRadiologistEntity(Radiologist radio) {
        RadiologistEntity res = new RadiologistEntity();
        CredentialsEntity credEnt = credDao.findByEmail(radio.getEmail()).orElseThrow(
                () -> new UserNotFoundException("No Such User")
        );
        res.setUserId(credEnt);
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
}
