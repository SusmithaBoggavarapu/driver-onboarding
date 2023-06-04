package com.uber.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends ApplicationException {

    public BadRequestException(Errors errors) {
        super(errors);
    }

    public BadRequestException(Errors errors, String message) {
        super(errors, message);
    }
}
