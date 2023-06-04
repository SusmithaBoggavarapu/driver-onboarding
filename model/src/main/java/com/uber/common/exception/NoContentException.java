package com.uber.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoContentException extends ApplicationException {

    public NoContentException(Errors errors) {
        super(errors);
    }

    public NoContentException(Errors errors, String message) {
        super(errors, message);
    }
}
