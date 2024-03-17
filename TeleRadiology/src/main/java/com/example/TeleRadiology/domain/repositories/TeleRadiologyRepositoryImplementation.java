package com.example.TeleRadiology.domain.repositories;

import org.springframework.stereotype.Component;

import com.example.TeleRadiology.data.dao.CredentialsDao;
import com.example.TeleRadiology.data.dao.RoleDao;
import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.RoleEntity;
import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.dto.CredentialsRequest;
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
        // RoleEntity roleEntity = roleDao.findById(role).orElse(null);
        // Credentials cred = new Credentials();
        // if (roleEntity != null) {
        // cred.setRole(role);
        // CredentialsEntity credEnt = credDao.findByEmailAndRole(email,
        // roleEntity).orElse(null);
        // if (credEnt != null) {
        // return mapToDomainCredentialsEntity(credEnt);
        // } else {
        // return cred;
        // }
        // return cred;
        RoleEntity roleEntity = roleDao.findById(role).orElseThrow(
                () -> new GlobalException("Wrong role"));
        CredentialsEntity credEnt = credDao.findByEmailAndRole(email, roleEntity).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        return mapToDomainCredentialsEntity(credEnt);
    }

    public int addPatient(CredentialsRequest cred) {
        if (credDao.existsByEmail(cred.getEmail())) {
            throw new GlobalException("EmailID Already Exists");
        }
        CredentialsEntity newUser = credDao.save(mapToCredEntity(cred));

        return newUser.getId();
    }

    public CredentialsEntity mapToCredEntity(CredentialsRequest cred) {

        RoleEntity roleEntity = roleDao.findById(cred.getRole()).orElseThrow(
                () -> new GlobalException("Wrong role"));
        CredentialsEntity newCred = new CredentialsEntity();
        newCred.setEmail(cred.getEmail());
        newCred.setPassword(cred.getPassword());
        newCred.setActive(1);
        newCred.setRole(roleEntity);

        return newCred;
    }

    private Credentials mapToDomainCredentialsEntity(CredentialsEntity credEntity) {
        Credentials cred = new Credentials();
        cred.setId(credEntity.getId());
        cred.setEmail(credEntity.getEmail());
        cred.setActive(credEntity.getActive());
        cred.setPassword(credEntity.getPassword());
        cred.setRole(credEntity.getRole().getId());
        return cred;
    }

}
