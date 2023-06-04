package com.uber.service;

import com.uber.common.exception.ApplicationException;
import com.uber.common.exception.BadRequestException;
import com.uber.common.exception.Errors;
import com.uber.common.model.DocumentType;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class DocumentValidationService {


    private final String TMP_FILE_PATH = "/tmp/";

    private final List<String> supportedFormats = List.of("jpeg", "jgp", "pdf");

    public void validateDocument(List<String> documents) {
        documents.forEach(document -> validateDocument(document));
    }

    public void validateDocument(String document) {
        File file = new File(TMP_FILE_PATH + document);
    }

    public void validate(Map<DocumentType, File> documents) {
        Set<DocumentType> allDocumentTypes = documents.keySet();
        if (allDocumentTypes.containsAll(Arrays.asList(DocumentType.values()))) {
            throw new ApplicationException(Errors.MISSING_DOCUMENTS);
        }

        for (Map.Entry<DocumentType, File> document : documents.entrySet()) {
            File file = document.getValue();
            validateExtension(file.getName());
        }

    }

    public void validateExtension(String fileName) {
        for (String supportedFormat : supportedFormats) {
            if (fileName.toLowerCase().endsWith(supportedFormat)) {
                return;
            }
        }
        throw new BadRequestException(Errors.UNSUPPORTED_FILE_FORMAT);
    }
}
