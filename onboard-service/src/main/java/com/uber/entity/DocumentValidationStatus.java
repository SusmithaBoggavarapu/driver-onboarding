package com.uber.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DocumentValidationStatus {
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("status")
    List<ValidationResponseStatus> documentStatus;

    @Data
    public static class ValidationResponseStatus {
        private String type;
        private boolean valid;
    }
}
