package com.example.imageStorage.domain;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.imageStorage.data.collections.AnnotationDocument;
import com.example.imageStorage.data.collections.ProfilePicDocument;
import com.example.imageStorage.data.collections.ReportDocument;
import com.example.imageStorage.data.dao.AnnotationDao;
import com.example.imageStorage.data.dao.ProfilePicDao;
import com.example.imageStorage.data.dao.ReportDao;
import com.example.imageStorage.dto.AnnotatedReportDTO;
import com.example.imageStorage.dto.GetAllReportsReq;
import com.example.imageStorage.dto.GetAllReportsRes;
import com.example.imageStorage.dto.ProfilePicDTO;
import com.example.imageStorage.dto.ReportDTO;
import com.example.imageStorage.exception.FailedToRetrieveException;
import com.example.imageStorage.exception.FailedToSaveException;
import com.example.imageStorage.exception.GlobalException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageStorageService {
    private final ReportDao repDao;
    private final AnnotationDao annDao;
    private final ProfilePicDao profileDao;
    private final WebClient webClient;

    public boolean addReport(String image, int id) {
        ReportDocument repDoc = new ReportDocument();
        repDoc.setReport(image);
        repDoc.setReportId(id);
        Optional.ofNullable(repDao.save(repDoc))
                .orElseThrow(() -> new FailedToSaveException("Failed to save entity to MongoDB"));
        return true;
    }

    public ReportDTO getReport(int id) {
        ReportDocument repDoc = repDao.findByReportId(id).orElseThrow(
                () -> new FailedToRetrieveException("Failed to find entity from MongoDB"));
        return mapToDTOReportDocument(repDoc);
    }

    public boolean addAnnotatedReport(String image, int id) {
        AnnotationDocument annDoc = new AnnotationDocument();
        annDoc.setAnnotation(image);
        annDoc.setAnnotationId(id);
        Optional.ofNullable(annDao.save(annDoc))
                .orElseThrow(() -> new FailedToSaveException("Failed to save entity to MongoDB"));
        return true;
    }

    public AnnotatedReportDTO getAnnotation(int id) {
        AnnotationDocument annDoc = annDao.findByAnnotationId(id).orElseThrow(
                () -> new FailedToRetrieveException("Failed to find entity from MongoDB"));
        return mapToDTOAnnotationDocument(annDoc);
    }

    public ProfilePicDTO getProfilePic(int id) {
        ProfilePicDocument profileDoc = profileDao.findByUserId(id).orElseThrow(
                () -> new FailedToRetrieveException("Failed to find entity from MongoDB"));
        return mapToDTOProfilePicDocument(profileDoc);
    }

    public GetAllReportsRes getAllReports(GetAllReportsReq req) {
        GetAllReportsRes res = new GetAllReportsRes();
        res.setReports(new ArrayList<>());
        for (Integer reportId : req.getReportIds()) {
            ReportDocument repDoc = repDao.findByReportId(reportId).orElseThrow(
                    () -> new FailedToRetrieveException("Failed to find entity from MongoDB"));
            res.getReports().add(mapToDTOReportDocument(repDoc));
        }
        return res;
    }

    public boolean addProfilePic(String image, int id) {
        ProfilePicDocument profilePic = new ProfilePicDocument();
        profilePic.setProfilePic(image);
        profilePic.setUserId(id);
        Optional.ofNullable(profileDao.save(profilePic))
                .orElseThrow(() -> new FailedToSaveException("Failed to save entity to MongoDB"));
        return true;
    }

    private ReportDTO mapToDTOReportDocument(ReportDocument repDoc) {
        ReportDTO repDto = new ReportDTO();
        repDto.setReportId(repDoc.getReportId());
        repDto.setReport(repDoc.getReport());
        return repDto;
    }

    private AnnotatedReportDTO mapToDTOAnnotationDocument(AnnotationDocument annDoc) {
        AnnotatedReportDTO annDto = new AnnotatedReportDTO();
        annDto.setAnnotatedImage(annDoc.getAnnotation());
        annDto.setAnnotationId(annDoc.getAnnotationId());
        return annDto;
    }

    private ProfilePicDTO mapToDTOProfilePicDocument(ProfilePicDocument profileDoc) {
        ProfilePicDTO profileDto = new ProfilePicDTO();
        profileDto.setProfilePic(profileDoc.getProfilePic());
        profileDto.setUserId(profileDoc.getUserId());
        return profileDto;
    }
}
