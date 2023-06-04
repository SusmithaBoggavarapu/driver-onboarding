package com.uber.controller;

import com.uber.client.AuthClient;
import com.uber.common.model.DocumentType;
import com.uber.common.response.Response;
import com.uber.service.DocumentService;
import com.uber.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping(value = {"/register/documents"})
public class RegisterDocumentsController extends BaseController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private FileService fileService;
    @Autowired
    private AuthClient authClient;

    @PutMapping(value = {"/{documentType}"})
    public ResponseEntity<Response> postDocument(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable String documentType, @RequestPart("file") MultipartFile file) throws IOException {

        String mobile = authClient.validateUser(authorization);

        if (Objects.isNull(file.getOriginalFilename())) {
            throw new IllegalArgumentException("file cannot be empty");
        }
        DocumentType docTypeEnum = EnumUtils.getEnumIgnoreCase(DocumentType.class, documentType);

        if (Objects.isNull(docTypeEnum)) {
            throw new IllegalArgumentException(String.format("unsupported document %s ", documentType));
        }


        File savedFile = postDocument(mobile, docTypeEnum, file);

       return  ResponseEntity.ok(Response.builder().status("SUCCESS").payload(savedFile.getName()).build());

    }

    @Transactional
    private File postDocument(String mobile, DocumentType docTypeEnum, MultipartFile file) {
        File savedFile = fileService.saveFile(file);
        documentService.saveDocument(mobile, savedFile, docTypeEnum);
        savedFile.delete();
        return savedFile;
    }

    @GetMapping(value = {"/{documentType}"})
    public ResponseEntity<byte[]> getDocument(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable String documentType) {

        String mobile = authClient.validateUser(authorization);

        DocumentType docTypeEnum = EnumUtils.getEnumIgnoreCase(DocumentType.class, documentType);
        if (Objects.isNull(docTypeEnum)) {
            throw new IllegalArgumentException(String.format("unsupported document %s ", documentType));
        }

        try {
            File file = documentService.getDocument(mobile, docTypeEnum);
            String headerValue = "attachment; filename=\"" + file.getName() + "\"";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .body(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
