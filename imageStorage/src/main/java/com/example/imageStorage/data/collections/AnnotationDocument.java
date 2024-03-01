package com.example.imageStorage.data.collections;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
@Document(collection = "annotations")
public class AnnotationDocument {
    @Field("annotation_id")
    private int annotationId;

    @Field("annotation")
    private String annotation;
}
