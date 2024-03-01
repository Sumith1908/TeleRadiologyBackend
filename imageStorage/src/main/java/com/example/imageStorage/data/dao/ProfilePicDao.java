package com.example.imageStorage.data.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.imageStorage.data.collections.ProfilePicDocument;

public interface ProfilePicDao extends MongoRepository<ProfilePicDocument, Integer> {
    Optional<ProfilePicDocument> findByUserId(int id);
}
