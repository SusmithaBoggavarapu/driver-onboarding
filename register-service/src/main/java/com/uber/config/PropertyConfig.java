package com.uber.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class PropertyConfig {

    @Value("${onboarding.topic}")
    private String onboardingTopic;

}