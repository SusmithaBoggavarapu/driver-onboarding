package com.uber.controller;

import com.uber.client.AuthClient;
import com.uber.common.response.Response;
import com.uber.service.OnboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = {"onboard/trigger"})
public class OnboardController extends BaseController{
    @Autowired
    private OnboardService onboardService;

    @Autowired
    private AuthClient authClient;

    @PostMapping
    public ResponseEntity<Response> onboard(@RequestHeader(HttpHeaders.AUTHORIZATION) String authToken) {
        String mobile = authClient.validateUser(authToken);
        log.info("onboarding user {} ", mobile);
        onboardService.triggerOnboarding(mobile);
        return ResponseEntity.ok(Response.builder().status("SUCCESS").payload("initiated onboarding process").build());
    }

}
