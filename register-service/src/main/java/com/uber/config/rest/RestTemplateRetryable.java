package com.uber.config.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Slf4j
public class RestTemplateRetryable extends RestTemplate {

    private final RetryTemplate retryTemplate;


    public RestTemplateRetryable(int retryMaxAttempts, long retryBackoffPeriodsMillisec) {
        this.retryTemplate = new CustomRetryTemplateBuilder()
                .withRetryMaxAttempts(retryMaxAttempts)
                .withRetryBackoffPeriodsMillisec(retryBackoffPeriodsMillisec)
                .withHttpStatus(HttpStatus.TOO_MANY_REQUESTS)
                .withHttpStatus(HttpStatus.BAD_GATEWAY)
                .withHttpStatus(HttpStatus.GATEWAY_TIMEOUT)
                .build();
    }

    @Override
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Object... uriVariables) throws RestClientException {
        return retryTemplate.execute(retryContext -> {
            logRetry(retryContext);
            return super.exchange(url, method, requestEntity, responseType, uriVariables);
        });
    }

    @Override
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return retryTemplate.execute(retryContext -> {
                    logRetry(retryContext);
                    return super.exchange(url, method, requestEntity, responseType, uriVariables);
                }
        );
    }

    @Override
    public <T> ResponseEntity<T> exchange(URI url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, Class<T> responseType) throws RestClientException {
        return retryTemplate.execute(retryContext -> {
            logRetry(retryContext);
            return super.exchange(url, method, requestEntity, responseType);
        });
    }

    @Override
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Object... uriVariables) throws RestClientException {
        return retryTemplate.execute(retryContext -> {
                    logRetry(retryContext);
                    return super.exchange(url, method, requestEntity, responseType, uriVariables);
                }
        );

    }

    @Override
    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
        return retryTemplate.execute(retryContext -> {
                    logRetry(retryContext);
                    return super.exchange(url, method, requestEntity, responseType, uriVariables);
                }
        );

    }

    @Override
    public <T> ResponseEntity<T> exchange(URI url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, ParameterizedTypeReference<T> responseType) throws RestClientException {
        return retryTemplate.execute(retryContext -> {
                    logRetry(retryContext);
                    return super.exchange(url, method, requestEntity, responseType);
                }
        );
    }

    @Override
    public <T> ResponseEntity<T> exchange(RequestEntity<?> entity, Class<T> responseType) throws RestClientException {
        return retryTemplate.execute(retryContext -> {
                    logRetry(retryContext);
                    return super.exchange(entity, responseType);
                }
        );

    }

    @Override
    public <T> ResponseEntity<T> exchange(RequestEntity<?> entity, ParameterizedTypeReference<T> responseType) throws RestClientException {
        return retryTemplate.execute(retryContext ->
                super.exchange(entity, responseType));
    }


    private void logRetry(RetryContext retryContext) {
        if (retryContext.getRetryCount() > 0) {
            Throwable error = retryContext.getLastThrowable();
            String errorMessage = "";
            if (error != null) {
                errorMessage = error.getMessage();
            }
            log.error(
                    "retry attempt={} last attempt error message={}  ",
                    retryContext.getRetryCount(),
                    errorMessage);
        }
    }
}
