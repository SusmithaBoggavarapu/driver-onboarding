package com.uber.controller;

import com.uber.entity.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import javax.validation.ConstraintViolationException;

@Slf4j
public class BaseController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Response<Payload>> handleException(ConstraintViolationException ex) {
        log.error("constraint voilation exception ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(HttpStatus.BAD_REQUEST.name(), null, new Response.Errors(HttpStatus.BAD_REQUEST.value(), ex.getMessage())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<Payload>> handleException(IllegalArgumentException ex) {
        log.error("illegal argument exception ", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response<>(HttpStatus.BAD_REQUEST.name(), null, new Response.Errors(HttpStatus.BAD_REQUEST.value(), ex.getMessage())));
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Response<Payload>> handleException(HttpClientErrorException ex) {
        log.error("http client exception ", ex);
        return ResponseEntity.status(ex.getStatusCode()).body(new Response<>(ex.getStatusCode().name(), null, new Response.Errors(HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getResponseBodyAsString())));
    }

    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<Response<Payload>> handleException(ResourceAccessException ex) {
        log.error("resource access exception ", ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new Response<>(HttpStatus.SERVICE_UNAVAILABLE.name(), null, new Response.Errors(HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Payload>> handleException(Exception ex) {
        log.error("exception ", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>(HttpStatus.INTERNAL_SERVER_ERROR.name(), null, new Response.Errors(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage())));
    }
}
