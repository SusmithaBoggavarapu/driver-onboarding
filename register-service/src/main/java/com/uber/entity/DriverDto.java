package com.uber.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class DriverDto {
    @NotEmpty(message = "firstname is required ")
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @NotEmpty(message = "mobile is required ")
    @JsonProperty("mobile")
    private String mobile;
    @NotEmpty(message = "password is required ")
    @JsonProperty("password")
    private String password;
    @JsonProperty("address")
    private Address address;
    @JsonProperty("authToken")
    private String authToken;

    @Data
    public static class Address {
        @JsonProperty("city")
        private String city;
        @JsonProperty("state")
        private String state;
        @JsonProperty("pincode")
        private int pincode;
    }
}
