package com.uber.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OnboardService {
    @Value("${onboarding.topic}")
    private String onboardingTopic;
    @Autowired
    private ProducerService producerService;

    public void onboard(String userId) {
        producerService.sendMessage(onboardingTopic, userId);
    }
}
