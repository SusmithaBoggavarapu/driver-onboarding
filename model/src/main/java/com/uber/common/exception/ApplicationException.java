package com.uber.common.exception;

import lombok.Data;

@Data
public class ApplicationException extends RuntimeException {
    private final Errors errors;

    public ApplicationException(Errors errors) {
        super(errors.getErrorMessage());
        this.errors = errors;
    }

    public ApplicationException(Errors errors, String errorMsg) {
        super(errorMsg);
        this.errors = errors;
    }

}