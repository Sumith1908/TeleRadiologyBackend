package com.example.TeleRadiology.domain.repositories;

import com.example.TeleRadiology.data.dao.CredentialsDao;
import com.example.TeleRadiology.data.dao.DoctorDao;
import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.DoctorEntity;
import com.example.TeleRadiology.data.entities.PatientEntity;
import com.example.TeleRadiology.domain.model.Doctor;
import com.example.TeleRadiology.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DoctorRepositoryImplementation implements DoctorRepository{
    private final CredentialsDao credDao;
    private final DoctorDao doctorDao;
    @Override
    public void addDoctor(Doctor doc) {
        doctorDao.save(mapToDoctorEntity(doc));
    }

    private DoctorEntity mapToDoctorEntity(Doctor doc) {
        DoctorEntity docEnt = new DoctorEntity();
        CredentialsEntity credEnt = credDao.findByEmail(doc.getHospitalEmail()).orElseThrow(
                () -> new UserNotFoundException("No Such User")
        );
//        docEnt = doctorDao.findByUserIdId(doc.getUserId()).orElse(null);
//        if (docEnt == null) {
//            docEnt = new DoctorEntity();
//        }
        docEnt.setUserId(credEnt);
        docEnt.setFirstName(doc.getFirstName());
        docEnt.setMiddleName(doc.getMiddleName());
        docEnt.setLastName(doc.getLastName());
        docEnt.setGender(doc.getGender());
        docEnt.setHospitalAddress(doc.getHospitalAddress());
        docEnt.setHospitalCity(doc.getHospitalCity());
        docEnt.setHospitalState(doc.getHospitalState());
        docEnt.setHospitalPinCode(doc.getHospitalPinCode());
        docEnt.setHospitalEmail(doc.getHospitalEmail());
        docEnt.setHospitalPhoneNumber(doc.getHospitalPhoneNumber());
        docEnt.setExperience(doc.getExperience());
        docEnt.setHighestEducation(doc.getHighestEducation());
        docEnt.setType(doc.getType());
        docEnt.setProfilePhoto(doc.getProfilePhoto());
        return docEnt;
    }
}
