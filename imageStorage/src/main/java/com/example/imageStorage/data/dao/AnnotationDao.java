package com.example.imageStorage.data.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.imageStorage.data.collections.AnnotationDocument;

public interface AnnotationDao extends MongoRepository<AnnotationDocument, Integer> {
    Optional<AnnotationDocument> findByAnnotationId(int id);
}
