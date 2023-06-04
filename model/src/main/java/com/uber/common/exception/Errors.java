package com.uber.common.exception;

import lombok.Getter;

@Getter
public enum Errors {
    EMPTY_FILE(40001, "empty file"),
    DUPLICATE_USER(40002,"user with mobile already exists"),
    UNAUTHORIZED_AUTH_CLIENT(40100,"unauthorized user "),
    UNAUTHORIZED_INVALID_TOKEN(40101,"invalid token "),
    MISSING_DOCUMENTS(40002, "missing documents"),
    NOT_FOUND_DOCUMENT(20400,"no document found for the given document type"),
    UNSUPPORTED_DOCUMENT_TYPE(40004, "unsupported document type"),
    THIRD_PARTY_BAD_REQUEST(40003, "bad request"),
    SERVER_ERROR(50003, "third party server error"),

    UNKNOWN_ERROR(60001, "unknown error"),
    PROCESSING_ERROR(50004, "unknown error");

    private int errorCode;
    private String errorMessage;

    Errors(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
