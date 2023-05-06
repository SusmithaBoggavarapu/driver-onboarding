package com.uber.service;

import com.uber.config.PropertyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class DocumentValidationService {
    @Autowired
    private BlobStorageService blobStorageService;

    @Autowired
    private PropertyConfig propertyConfig;

    private final String TMP_FILE_PATH = "/tmp/";

    public void validateDocument(List<String> documents) {
        documents.forEach(document -> validateDocument(document));
    }

    public void validateDocument(String document) {
        File file = new File(TMP_FILE_PATH + document);
        blobStorageService.downloadFile(file, propertyConfig.getContainerName());
    }
}
