package com.uber.service;

import com.uber.common.exception.BaseException;
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

    public void validateDocument(List<String> documents) {
        documents.forEach(document -> validateDocument(document));
    }

    public void validateDocument(String document) {
        File file = new File(TMP_FILE_PATH + document);
    }

    public void validate(Map<DocumentType, File> documents) {
        Set<DocumentType> allDocumentTypes = documents.keySet();
        if (allDocumentTypes.containsAll(Arrays.asList(DocumentType.values()))) {
            throw new BaseException(Errors.MISSING_DOCUMENTS);
        }

        for (Map.Entry<DocumentType, File> document : documents.entrySet()) {
            File file = document.getValue();
            if (!file.getName().contains(".pdf")) {
                throw new BaseException(Errors.UNSUPPORTED_DOCUMENT_TYPE);
            }
        }

    }
}
