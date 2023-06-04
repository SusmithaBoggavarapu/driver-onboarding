package com.uber.entity;

import com.uber.entity.user.User;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwtToken;
    private User user;
}
