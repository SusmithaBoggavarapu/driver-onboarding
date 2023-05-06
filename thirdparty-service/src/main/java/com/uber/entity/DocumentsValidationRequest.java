package com.uber.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DocumentsValidationRequest {
    @JsonProperty("document_type")
    private String documentType;
    @JsonProperty("documents")
    private List<String> documents;
}
