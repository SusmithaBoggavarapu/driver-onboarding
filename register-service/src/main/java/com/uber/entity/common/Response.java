package com.uber.entity.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {
    private String status;
    private T payload;
    private Errors error;

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Errors {
        int code;
        String errorCode;
    }
}