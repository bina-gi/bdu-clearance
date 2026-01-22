package com.bdu.clearance.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    
 
    String storeFile(MultipartFile file);

    void deleteFile(String filename);
}
