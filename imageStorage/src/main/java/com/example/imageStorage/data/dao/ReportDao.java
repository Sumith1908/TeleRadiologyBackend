package com.example.imageStorage.data.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.imageStorage.data.collections.ReportDocument;

public interface ReportDao extends MongoRepository<ReportDocument, Integer> {

}
