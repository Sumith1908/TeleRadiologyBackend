package com.example.TeleRadiology.domain.repositories;

import org.springframework.stereotype.Component;

import com.example.TeleRadiology.data.dao.CredentialsDao;
import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminRepositoryImplementation implements AdminRepository {
    private final CredentialsDao credDao;

    @Override
    public Boolean deactivateUser(int id) {
        CredentialsEntity credent = credDao.findById(id).orElseThrow(
                () -> new UserNotFoundException("NO SUCH USER"));
        credent.setActive(0);
        credDao.save(credent);

        return null;
    }
}
