package com.uber.controller;

import com.uber.common.exception.ApplicationException;
import com.uber.common.exception.BadRequestException;
import com.uber.common.exception.Errors;
import com.uber.common.exception.NoContentException;
import com.uber.common.exception.UnauthorizedException;
import com.uber.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.ConstraintViolationException;
import java.io.FileNotFoundException;
import java.util.Objects;

@Slf4j
public class BaseController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException ex) {
        StringBuilder errorMsg = new StringBuilder();
        ex.getConstraintViolations().stream().forEach(constraintViolation -> errorMsg.append(constraintViolation.getMessage()));
        log.error("constraint violation exception ", ex);
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.name(), errorMsg.toString()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException ex) {
        log.error("illegal argument exception ", ex);
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleException(HttpClientErrorException ex) {
        log.error("http client exception ", ex);
        return ResponseEntity.status(ex.getStatusCode()).body(new ErrorResponse(ex.getStatusCode().name(), ex.getResponseBodyAsString()));
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceAccessException ex) {
        log.error("resource access exception ", ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.name(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("exception ", ex);
        return ResponseEntity.internalServerError().body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage()));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(FileNotFoundException ex) {
        log.error("exception ", ex);
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleException(BadRequestException ex) {
        log.error("bad request exception ", ex);
        Errors error = ex.getErrors();
        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(error.getErrorCode()), error.getErrorMessage());

        if (Objects.nonNull(ex.getMessage()) && !ex.getMessage().equalsIgnoreCase(error.getErrorMessage())) {
            errorResponse.setErrorDescription(errorResponse.getErrorDescription() + StringUtils.SPACE + ex.getMessage());
        }
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleException(UnauthorizedException ex) {
        log.error("unauthorized request exception ", ex);
        Errors error = ex.getErrors();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(String.valueOf(error.getErrorCode()), error.getErrorMessage()));
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ErrorResponse> handleException(NoContentException ex) {
        log.error("no content  exception ", ex);
        Errors error = ex.getErrors();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorResponse(String.valueOf(error.getErrorCode()), error.getErrorMessage()));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleException(ApplicationException ex) {
        log.error("application exception ", ex);
        Errors error = ex.getErrors();
        return ResponseEntity.internalServerError().body(new ErrorResponse(String.valueOf(error.getErrorCode()), error.getErrorMessage()));
    }
}
