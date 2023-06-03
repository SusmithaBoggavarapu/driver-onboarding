package com.uber.config.exception;

import com.uber.common.exception.Errors;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

public class ThirdPartyResponseHandler implements ResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            throw new OnboardException(Errors.THIRD_PARTY_BAD_REQUEST);
        }

        if (response.getStatusCode().is5xxServerError()) {
            throw new OnboardException(Errors.THIRD_PARTY_SERVER_ERROR);
        }

        if (response.getStatusCode().isError()) {
            throw new OnboardException(Errors.UNKNOWN_ERROR);
        }
    }

    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }
}