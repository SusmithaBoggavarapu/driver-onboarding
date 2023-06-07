package com.uber.common.exception;

import lombok.Getter;

@Getter
public enum Errors {
    EMPTY_FILE("REG_DC_1", "empty file"),
    DUPLICATE_USER("REG_UR_1","user with mobile already exists"),
    UNAUTHORIZED_AUTH_CLIENT("AUTH_UR_1","unauthorized user "),
    UNAUTHORIZED_INVALID_TOKEN("AUTH_UR_2","invalid token "),
    MISSING_DOCUMENTS("REG_DC_2", "missing documents"),
    NOT_FOUND_DOCUMENT("REG_DC_3","no document found for the given document type"),
    UNSUPPORTED_DOCUMENT_TYPE("REG_DC_4", "unsupported document type"),
    UNSUPPORTED_FILE_FORMAT("REG_DC_5", "unsupported file format"),
    THIRD_PARTY_BAD_REQUEST("OB_TP_1", "bad request"),
    INVALID_ONBOARD_REQUEST("OB_RQ_1", "either tracking device not sent / documents are not validated"),
    SERVER_ERROR("OB_TP_2", "third party server error"),

    UNKNOWN_ERROR("CMN_UR_1", "unknown error"),
    PROCESSING_ERROR("CMN_PR_1", "unknown error");

    private String errorCode;
    private String errorMessage;

    Errors(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
