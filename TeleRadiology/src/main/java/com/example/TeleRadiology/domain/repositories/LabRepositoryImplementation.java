package com.example.TeleRadiology.domain.repositories;

import com.example.TeleRadiology.data.dao.CredentialsDao;
import com.example.TeleRadiology.data.dao.LabDao;
import com.example.TeleRadiology.data.dao.RadiologistDao;
import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.LabEntity;
import com.example.TeleRadiology.domain.model.Lab;
import com.example.TeleRadiology.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LabRepositoryImplementation implements LabRepository{
    private final CredentialsDao credDao;
    private final LabDao labDao;
    public void addLab(Lab lab, int id) {
        labDao.save(mapToLabEntity(lab, id));
    }

    private LabEntity mapToLabEntity(Lab lab, int id) {
        LabEntity res = new LabEntity();
        CredentialsEntity credEnt = credDao.findById(id).orElseThrow(
                () -> new UserNotFoundException("No Such User")
        );
        res.setUserId(credEnt);
        res.setName(lab.getName());
        res.setAddress(lab.getAddress());
        res.setCity(lab.getCity());
        res.setState(lab.getState());
        res.setPinCode(lab.getPinCode());
        res.setPhoneNumber(lab.getPhoneNumber());
        return res;
    }
}
