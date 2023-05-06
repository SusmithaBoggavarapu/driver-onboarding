package com.uber.controller;

import com.uber.entity.DocumentsValidationRequest;
import com.uber.service.DocumentValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = {"documents/validate/{userId}"})
public class DocumentValidationController {

    @Autowired
    private DocumentValidationService documentValidationService;

    @PostMapping
    public ResponseEntity<Boolean> validate(@PathVariable String userId, @RequestBody DocumentsValidationRequest documentsValidationRequest) {
        log.info("validating documents for the user {} {} ", userId, documentsValidationRequest);
        documentValidationService.validateDocument(documentsValidationRequest.getDocuments());
        return ResponseEntity.ok(null);
    }
}
