package com.uber.auth.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDto {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
}
