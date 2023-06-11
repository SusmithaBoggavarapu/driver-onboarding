package com.uber.config;

import com.uber.config.rest.RestTemplateRetryable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
@EnableRetry
public class AppConfig {
    @Bean
    public RestTemplate restTemplate(@Value("${rest-template.retry.max-attempts}") int retryMaxAttempts, @Value("${rest-template.retry.backoff.period.millisecs}") long retryBackoffPeriodsMillisec) {
        return new RestTemplateRetryable(retryMaxAttempts, retryBackoffPeriodsMillisec);
    }

}
