package com.example.imageStorage.data.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.imageStorage.data.collections.ReportDocument;

public interface ReportDao extends MongoRepository<ReportDocument, Integer> {
    Optional<ReportDocument> findByReportId(int id);

}
