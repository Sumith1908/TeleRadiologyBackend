package com.example.TeleRadiology.domain.repositories;

import com.example.TeleRadiology.data.dao.*;
import com.example.TeleRadiology.dto.ChangePasswordReq;
import org.springframework.stereotype.Component;

import com.example.TeleRadiology.data.entities.CredentialsEntity;
import com.example.TeleRadiology.data.entities.RoleEntity;
import com.example.TeleRadiology.data.entities.SaltEntity;
import com.example.TeleRadiology.domain.model.Credentials;
import com.example.TeleRadiology.dto.CredentialsRequest;
import com.example.TeleRadiology.exception.GlobalException;
import com.example.TeleRadiology.exception.UserNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TeleRadiologyRepositoryImplementation implements TeleRadiologyRepository {
    private final CredentialsDao credDao;
    private final RoleDao roleDao;
    private final SaltDao saltDao;
    private final ValidTokensDao tokenDao;
    private final OtpDao otpDao;

    @Override
    public Credentials checkLoginCredentials(String email, String role) {
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
        RoleEntity roleEntity = roleDao.findByRole(role).orElseThrow(
                () -> new GlobalException("Wrong role"));
        CredentialsEntity credEnt = credDao.findByEmailAndRoleId(email, roleEntity.getId()).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        return mapToDomainCredentialsEntity(credEnt);
    }

    public Credentials getUserByEmail(String email) {
        return mapToDomainCredentialsEntity(
                credDao.findByEmail(email).orElseThrow(() -> new UserNotFoundException("No such User")));
    }

    public int addPatient(CredentialsRequest cred) {
        if (credDao.existsByEmail(cred.getEmail())) {
            throw new GlobalException("EmailID Already Exists");
        }
        CredentialsEntity newUser = credDao.save(mapToCredEntity(cred));

        return newUser.getId();
    }

    public void changePassword(ChangePasswordReq changePasswordReq) {

        CredentialsEntity credentialsEntity = new CredentialsEntity();

        credentialsEntity=credDao.findById(changePasswordReq.getId()).orElseThrow(() -> new UserNotFoundException("No such User"));
        credentialsEntity.setPassword(changePasswordReq.getPassword());

        credDao.save(credentialsEntity);
    }

    public void addSalt(int id, String salt) {
        SaltEntity saltEnt = new SaltEntity();
        CredentialsEntity user = credDao.findById(id).orElse(null);
        saltEnt.setUserId(user);
        saltEnt.setSalt(salt);
        saltDao.save(saltEnt);
    }

    public String getSalt(int id) {
        SaltEntity salt = saltDao.findByUserIdId(id).orElseThrow(
                () -> new UserNotFoundException("No such User"));
        return salt.getSalt();
    }

    public CredentialsEntity mapToCredEntity(CredentialsRequest cred) {

        RoleEntity roleEntity = roleDao.findByRole(cred.getRole()).orElseThrow(
                () -> new GlobalException("Wrong role"));
        CredentialsEntity newCred = new CredentialsEntity();
        newCred.setEmail(cred.getEmail());
        newCred.setPassword(cred.getPassword());
        newCred.setActive(1);
        newCred.setRole(roleEntity);

        return newCred;
    }

    @Transactional
    public void deleteToken(String token) {
        try {
            tokenDao.deleteByToken(token);
        } catch (Exception e) {
            throw new GlobalException("Could not delete token");
        }
    }

    private Credentials mapToDomainCredentialsEntity(CredentialsEntity credEntity) {
        Credentials cred = new Credentials();
        cred.setId(credEntity.getId());
        cred.setEmail(credEntity.getEmail());
        cred.setActive(credEntity.getActive());
        cred.setPassword(credEntity.getPassword());
        cred.setRole(credEntity.getRole().getRole());
        return cred;
    }
}