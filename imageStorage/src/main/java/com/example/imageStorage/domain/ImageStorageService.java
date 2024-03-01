package com.example.imageStorage.domain;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.imageStorage.data.collections.AnnotationDocument;
import com.example.imageStorage.data.collections.ProfilePicDocument;
import com.example.imageStorage.data.collections.ReportDocument;
import com.example.imageStorage.data.dao.AnnotationDao;
import com.example.imageStorage.data.dao.ProfilePicDao;
import com.example.imageStorage.data.dao.ReportDao;
import com.example.imageStorage.exception.FailedToSaveReportException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageStorageService {
    private final ReportDao repDao;
    private final AnnotationDao annDao;
    private final ProfilePicDao profileDao;

    public boolean addReport(String image, int id) {
        ReportDocument repDoc = new ReportDocument();
        repDoc.setReport(image);
        repDoc.setReportID(id);
        Optional.ofNullable(repDao.save(repDoc))
                .orElseThrow(() -> new FailedToSaveReportException("Failed to save entity to MongoDB"));
        return true;
    }

    public boolean addAnnotatedReport(String image, int id) {
        AnnotationDocument annDoc = new AnnotationDocument();
        annDoc.setAnnotation(image);
        annDoc.setAnnotationId(id);
        Optional.ofNullable(annDao.save(annDoc))
                .orElseThrow(() -> new FailedToSaveReportException("Failed to save entity to MongoDB"));
        return true;
    }

    public boolean addProfilePic(String image, int id) {
        ProfilePicDocument profilePic = new ProfilePicDocument();
        profilePic.setProfilePic(image);
        profilePic.setUserID(id);
        Optional.ofNullable(profileDao.save(profilePic))
                .orElseThrow(() -> new FailedToSaveReportException("Failed to save entity to MongoDB"));
        return true;
    }
}
