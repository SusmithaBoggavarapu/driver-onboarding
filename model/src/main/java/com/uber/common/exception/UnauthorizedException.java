package com.uber.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends ApplicationException {

    public UnauthorizedException(Errors errors) {
        super(errors);
    }

    public UnauthorizedException(Errors errors, String message) {
        super(errors, message);
    }
}
