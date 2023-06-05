package com.uber.service;

import com.uber.common.exception.Errors;
import com.uber.common.model.DocumentType;
import com.uber.config.exception.OnboardException;
import com.uber.config.exception.ThirdPartyResponseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.Map;

@Component
@Slf4j
public class DocumentVerificationClient {

    @Value("${verification.url}")
    private String uri;

    private static final String REQUEST_ID = "request_id";
    private static final String NAME = "name";
    private static final String MOBILE = "mobile";

    public String submitDocuments(String requestId, String name, String mobile, Map<DocumentType, File> documents) {
        log.info("submitting documents for validation requestId: {} mobile: {} documents: {} ", requestId, mobile, documents);

        MultiValueMap<String, Object> requestMap = getRequestMap(requestId, name, mobile, documents);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestMap, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new ThirdPartyResponseHandler());
        ResponseEntity<String> response;

        try {

            response = restTemplate.exchange(uri,
                    HttpMethod.POST, requestEntity, String.class);

            log.info("response status {} " + response.getStatusCode());
            log.info("response body returned from third party client {} ", response.getBody());
            return response.getBody();

        } catch (HttpClientErrorException ex) {
            throw new OnboardException(Errors.THIRD_PARTY_BAD_REQUEST, ex.getMessage());
        } catch (HttpServerErrorException ex) {
            throw new OnboardException(Errors.SERVER_ERROR, ex.getMessage());
        } catch (Exception ex) {
            //throw new OnboardException(Errors.UNKNOWN_ERROR, ex.getMessage());
            return "test";

        }

    }

    private MultiValueMap<String, Object> getRequestMap(String name, String mobile, String requestId, Map<DocumentType, File> documents) {

        MultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<>();
        for (Map.Entry<DocumentType, File> document : documents.entrySet()) {
            requestMap.add(document.getKey().name().toLowerCase(), new FileSystemResource(document.getValue()));
        }
        requestMap.add(REQUEST_ID, requestId);
        requestMap.add(MOBILE, mobile);
        requestMap.add(NAME, name);

        return requestMap;
    }

}
