package com.bdu.clearance.services.impl;

import com.bdu.clearance.exceptions.APIException;
import com.bdu.clearance.services.FileStorageService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    private Path uploadPath;

    @PostConstruct
    public void init() {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new APIException("Could not create upload directory: " + e.getMessage());
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new APIException("Cannot store empty file");
        }

        // Get original filename and clean it
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        
        // Validate filename
        if (originalFilename.contains("..")) {
            throw new APIException("Filename contains invalid path sequence: " + originalFilename);
        }

        // Generate unique filename to prevent conflicts
        String fileExtension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            fileExtension = originalFilename.substring(dotIndex);
        }
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

        try {
            // Copy file to upload directory
            Path targetLocation = this.uploadPath.resolve(uniqueFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // Return the URL path to access the file
            return "/uploads/" + uniqueFilename;
        } catch (IOException e) {
            throw new APIException("Could not store file " + originalFilename + ": " + e.getMessage());
        }
    }

    @Override
    public void deleteFile(String filename) {
        try {
            Path filePath = this.uploadPath.resolve(filename).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new APIException("Could not delete file " + filename + ": " + e.getMessage());
        }
    }
}
