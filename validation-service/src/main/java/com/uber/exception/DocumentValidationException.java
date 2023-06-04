package com.uber.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class DocumentValidationException extends RuntimeException {
    private final Errors errors;

    public DocumentValidationException(Errors errors) {
        super(errors.getErrorMessage());
        this.errors = errors;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Errors {
        private String errorCode;
        private String errorMessage;
    }
}


