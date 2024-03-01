package com.example.imageStorage.data.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.imageStorage.data.collections.ProfilePicDocument;

public interface ProfilePicDao extends MongoRepository<ProfilePicDocument, Integer> {

}
