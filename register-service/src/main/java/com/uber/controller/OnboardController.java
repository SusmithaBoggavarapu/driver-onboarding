package com.uber.controller;

import com.uber.entity.common.Response;
import com.uber.service.OnboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = {"onboard/{userId}"})
public class OnboardController {
    @Autowired
    private OnboardService onboardService;

    @PostMapping
    public ResponseEntity<Response<String>> onboard(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken, @PathVariable String userId) {
        log.info("onboarding user {} ", userId);
        onboardService.onboard(userId);
        return ResponseEntity.ok(null);
    }
}
