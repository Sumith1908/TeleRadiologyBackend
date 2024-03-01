package com.example.imageStorage.data.collections;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "reports")
public class ReportDocument {
    @Field("report_id")
    private int reportId;

    @Field("report")
    private String report;
}
