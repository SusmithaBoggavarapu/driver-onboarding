package com.uber.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.common.exception.ApplicationException;
import com.uber.common.exception.Errors;
import com.uber.config.AppConfig;
import com.uber.config.AuthResponseHandler;
import com.uber.entity.AuthenticationResponse;
import com.uber.entity.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class AuthClient {

    @Value("${auth.url}")
    private String uri;

    @Autowired
    private RestTemplate restTemplate;

    private static final String REGISTER_ENDPOINT = "user/register";
    private static final String AUTHENTICATE_ENDPOINT = "user/authenticate";

    private static final String VALIDATION_ENDPOINT = "validateUser";


    public AuthenticationResponse registerOrAuthenticate(String userName, String password, String url) {
        log.info("auth url: {}, user: {} ", url, userName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<AuthenticationResponse> response;

        try {
            String requestJson = new ObjectMapper().writeValueAsString(new UserDto(userName, password));

            log.info("requestJson {} ", requestJson);
            HttpEntity<String> requestEntity = new HttpEntity<>(requestJson, headers);

            RestTemplate restTemplate = new AppConfig().restTemplate(3,3500);
            restTemplate.setErrorHandler(new AuthResponseHandler());

            response = restTemplate.exchange(url,
                    HttpMethod.POST, requestEntity, AuthenticationResponse.class);

            log.info("response status {} " + response.getStatusCode());
            log.info("response body returned from third party client {} ", response.getBody());

        } catch (JsonProcessingException ex) {
            throw new ApplicationException(Errors.PROCESSING_ERROR, ex.getMessage());
        }
        return response.getBody();

    }

    public AuthenticationResponse authenticate(String userName, String password) {
        register(userName, password);
        return registerOrAuthenticate(userName, password, uri + AUTHENTICATE_ENDPOINT);
    }

    public void register(String userName, String password) {
        registerOrAuthenticate(userName, password, uri + REGISTER_ENDPOINT);
    }

    public String validateUser(String authToken) {
        String url = uri + VALIDATION_ENDPOINT ;
        log.info("validating user url {} , authToken{} ", url, authToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "*/*");
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
        headers.setBearerAuth(authToken);
        ResponseEntity<String> response;

        HttpEntity<String> requestEntity = new HttpEntity<>(StringUtils.EMPTY, headers);

        restTemplate.setErrorHandler(new AuthResponseHandler());

        response = restTemplate.exchange(url,
                HttpMethod.GET, requestEntity, String.class);

        log.info("response status {} " + response.getStatusCode());
        log.info("response body {} " + response.getBody());
        return response.getBody();
    }

}
