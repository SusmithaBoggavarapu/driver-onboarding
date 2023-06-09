package com.uber.config.rest;

import org.springframework.classify.Classifier;
import org.springframework.http.HttpStatus;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.HashSet;
import java.util.Set;

public class CustomRetryTemplateBuilder {

    private static final int DEFAULT_MAX_ATTEMPS = 3;
    private static final int DEFAULT_BACKOFF_PERIODS_MILLI_SEC = 3500;

    private final Set<HttpStatus> httpStatusRetry;

    private int retryMaxAttempts = DEFAULT_MAX_ATTEMPS;
    private long retryBackoffPeriodsMillisecond = DEFAULT_BACKOFF_PERIODS_MILLI_SEC;


    public CustomRetryTemplateBuilder() {
        this.httpStatusRetry = new HashSet<>();
    }

    public CustomRetryTemplateBuilder withHttpStatus(HttpStatus httpStatus) {
        this.httpStatusRetry.add(httpStatus);
        return this;
    }

    public CustomRetryTemplateBuilder withRetryMaxAttempts(int retryMaxAttempts) {
        this.retryMaxAttempts = retryMaxAttempts;
        return this;
    }

    public CustomRetryTemplateBuilder withRetryBackoffPeriodsMillisec(long retryBackoffPeriodsMillisec) {
        this.retryBackoffPeriodsMillisecond = retryBackoffPeriodsMillisec;
        return this;
    }

    public RetryTemplate build() {
        if (this.httpStatusRetry.isEmpty()) {
            this.httpStatusRetry.addAll(getDefaults());
        }
        return createRetryTemplate();
    }

    private RetryTemplate createRetryTemplate() {
        RetryTemplate retry = new RetryTemplate();
        ExceptionClassifierRetryPolicy policy = new ExceptionClassifierRetryPolicy();
        policy.setExceptionClassifier(configureStatusCodeBasedRetryPolicy());
        retry.setRetryPolicy(policy);

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(retryBackoffPeriodsMillisecond);
        retry.setBackOffPolicy(fixedBackOffPolicy);

        return retry;
    }

    private Classifier<Throwable, RetryPolicy> configureStatusCodeBasedRetryPolicy() {
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(1 + this.retryMaxAttempts);
        NeverRetryPolicy neverRetryPolicy = new NeverRetryPolicy();

        return throwable -> {
            if (throwable instanceof HttpStatusCodeException) {
                HttpStatusCodeException httpException = (HttpStatusCodeException) throwable;
                return getRetryPolicyForStatus(httpException.getStatusCode(), simpleRetryPolicy, neverRetryPolicy);
            }
            return neverRetryPolicy;
        };
    }

    private RetryPolicy getRetryPolicyForStatus(HttpStatus httpStatusCode, SimpleRetryPolicy simpleRetryPolicy, NeverRetryPolicy neverRetryPolicy) {

        if (this.httpStatusRetry.contains(httpStatusCode)) {
            return simpleRetryPolicy;
        }
        return neverRetryPolicy;
    }

    private Set<HttpStatus> getDefaults() {
        return Set.of(
                HttpStatus.SERVICE_UNAVAILABLE,
                HttpStatus.BAD_GATEWAY,
                HttpStatus.GATEWAY_TIMEOUT
        );
    }
}
