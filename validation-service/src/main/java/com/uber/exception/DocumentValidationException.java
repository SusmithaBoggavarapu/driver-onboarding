package com.uber.exception;

import com.uber.common.exception.ApplicationException;
import com.uber.common.exception.Errors;

public class DocumentValidationException extends ApplicationException {

    public DocumentValidationException(Errors errors) {
        super(errors);
    }

}


