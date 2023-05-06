package com.uber.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

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
    @JsonProperty("address")
    private Address address;
    @Data
    public static class Address{
        @JsonProperty("city")
        private String city;
        @JsonProperty("state")
        private String state;
        @JsonProperty("pincode")
        private int pincode;
    }

    private Date createdOn;
    private Date updatedOn;
    private Date onboardedOn;
}
