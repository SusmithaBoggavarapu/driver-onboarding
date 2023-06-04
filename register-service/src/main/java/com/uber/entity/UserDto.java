package com.uber.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
}
