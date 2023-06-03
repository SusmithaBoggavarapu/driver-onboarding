package com.uber.config.exception;

import com.uber.common.exception.BaseException;
import com.uber.common.exception.Errors;

public class OnboardException extends BaseException {
    public OnboardException(Errors errors) {
        super(errors);
    }

    public OnboardException(Errors errors, String errorMsg) {
        super(errors, errorMsg);
    }
}
