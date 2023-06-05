package com.uber.controller;

import com.uber.entity.DocumentValidationStatus;
import com.uber.service.OnboardStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = {"onboard/documents/validate"})
public class ReceiveDocumentStatusController {

    @Autowired
    private OnboardStatusService onboardStatusService;

    @PostMapping
    public void documentStatusUpdate(@RequestBody DocumentValidationStatus documentValidationStatus, @RequestHeader(HttpHeaders.AUTHORIZATION) String authToken ) {
        log.info("received document updates {} ", documentValidationStatus);
        onboardStatusService.onboardAndSendTrackingDevice(documentValidationStatus);
    }

}
