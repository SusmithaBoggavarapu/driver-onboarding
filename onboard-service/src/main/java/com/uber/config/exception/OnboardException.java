package com.uber.config.exception;

import com.uber.common.exception.ApplicationException;
import com.uber.common.exception.Errors;

public class OnboardException extends ApplicationException {
    public OnboardException(Errors errors) {
        super(errors);
    }

    public OnboardException(Errors errors, String errorMsg) {
        super(errors, errorMsg);
    }
}
