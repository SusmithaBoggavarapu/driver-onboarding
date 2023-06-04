package com.uber.config;

import com.uber.common.exception.ApplicationException;
import com.uber.common.exception.Errors;
import com.uber.common.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Slf4j
public class AuthResponseHandler implements ResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()) {
            log.error("client exception {}", response.getStatusText());
            throw new UnauthorizedException(Errors.UNAUTHORIZED_AUTH_CLIENT);
        }

        if (response.getStatusCode().is5xxServerError()) {
            log.error("server exception {}", response.getStatusText());
            throw new ApplicationException(Errors.SERVER_ERROR);
        }

        if (response.getStatusCode().isError()) {
            log.error("exception {}", response.getStatusText());
            throw new ApplicationException(Errors.UNKNOWN_ERROR);
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