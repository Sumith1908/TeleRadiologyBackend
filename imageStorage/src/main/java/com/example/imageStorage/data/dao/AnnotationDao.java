package com.example.imageStorage.data.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.imageStorage.data.collections.AnnotationDocument;

public interface AnnotationDao extends MongoRepository<AnnotationDocument, Integer> {

}
