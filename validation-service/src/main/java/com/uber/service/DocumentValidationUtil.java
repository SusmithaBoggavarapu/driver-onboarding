package com.uber.service;

import com.uber.common.exception.ApplicationException;
import com.uber.common.exception.BadRequestException;
import com.uber.common.exception.Errors;
import com.uber.common.model.DocumentType;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DocumentValidationUtil {


    private static final String TMP_FILE_PATH = "/tmp/";

    private static final List<String> supportedFormats = List.of(".jpeg", ".jgp", ".pdf");

    public static void validateDocument(List<String> documents) {
        documents.forEach(document -> validateDocument(document));
    }

    public static void validateDocument(String document) {
        File file = new File(TMP_FILE_PATH + document);
    }

    public static void validate(Map<DocumentType, File> documents) {
        Set<DocumentType> allDocumentTypes = documents.keySet();
        List<DocumentType> mandatoryDocuments = new LinkedList<>(Arrays.asList(DocumentType.values()));

        if (!allDocumentTypes.containsAll(mandatoryDocuments)) {
            mandatoryDocuments.removeAll(allDocumentTypes);
            throw new BadRequestException(Errors.MISSING_DOCUMENTS, mandatoryDocuments.toString());
        }

        for (Map.Entry<DocumentType, File> document : documents.entrySet()) {
            File file = document.getValue();
            validateExtension(file.getName());
        }

    }

    public static void validateExtension(String fileName) {
        for (String supportedFormat : supportedFormats) {
            if (fileName.toLowerCase().endsWith(supportedFormat)) {
                return;
            }
        }
        throw new BadRequestException(Errors.UNSUPPORTED_FILE_FORMAT, FilenameUtils.getExtension(fileName));
    }
}
