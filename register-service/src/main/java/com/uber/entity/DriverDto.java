package com.uber.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DriverDto {
    @JsonProperty("firstname")
    private String firstName;
    @JsonProperty("lastname")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("mobile")
    private String mobile;
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
