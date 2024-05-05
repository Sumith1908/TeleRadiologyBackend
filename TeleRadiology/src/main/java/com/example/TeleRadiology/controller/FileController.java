package com.example.TeleRadiology.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.TeleRadiology.dto.UploadRequest;

import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teleRadiology")
public class FileController {
    @Value("${image.path}")
    private String imagePath;

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable int id) throws IOException {
        // Path to the file you want to send
        Path filePath = Paths.get(
                imagePath.concat("/" + Integer.toString(id) + ".dcm"));
        // Path filePath = Paths.get(imagePath + "/0002.dcm");

        // Load file as byte array
        byte[] data = Files.readAllBytes(filePath);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.dcm");

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @PostMapping("/saveAnnotation")
    public ResponseEntity<String> saveAnnotation(@RequestBody byte[] imageData) {
        try {
            // Create the file path
            Path filePath = Paths.get(
                    "D:/VS_Code/VS_Code_general_Workspace/IIITB/NewHAD/Backend/TeleRadiology/src/main/images/annotated.dcm");

            // Write the file data to the specified path
            Files.write(filePath, imageData);

            return ResponseEntity.ok("Annotation saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save annotation.");
        }
    }

    @PostMapping("/upload/{id}")
    public String uploadFile(@RequestParam("file") MultipartFile file, @PathVariable int id) {
        if (file.isEmpty()) {
            return "Please select a file to upload";
        }

        try {
            // String fileName = file.getOriginalFilename();
            File destFile = new File(imagePath.concat("/" + Integer.toString(id) + ".dcm"));
            file.transferTo(destFile);
            return "File uploaded successfully";
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to upload file";
        }
    }
}