package com.example.TeleRadiology.data;

import org.springframework.stereotype.Component;

import com.example.TeleRadiology.data.dao.CredentialsDao;
import com.example.TeleRadiology.data.dao.RoleDao;
import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.RoleEntity;
import com.example.TeleRadiology.domain.TeleRadiologyRepository;
import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.exception.GlobalException;
import com.example.TeleRadiology.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TeleRadiologyRepositoryImplementation implements TeleRadiologyRepository {
    private final CredentialsDao credDao;
    private final RoleDao roleDao;

    @Override
    public Credentials checkLoginCredentials(String email, int role) {
        RoleEntity roleEntity = roleDao.findById(role).orElseThrow(
                () -> new GlobalException("Wrong role ID"));
        CredentialsEntity cred = credDao.findByEmailAndRole(email, roleEntity).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        return mapToDomainCredentialsEntity(cred);
    }

    Credentials mapToDomainCredentialsEntity(CredentialsEntity credEntity) {
        Credentials cred = new Credentials();
        cred.setId(credEntity.getId());
        cred.setEmail(credEntity.getEmail());
        cred.setActive(credEntity.getActive());
        cred.setPassword(credEntity.getPassword());
        cred.setRole(credEntity.getRole().getId());
        return cred;
    }

}
