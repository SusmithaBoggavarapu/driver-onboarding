package com.uber.auth;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public boolean isValidUser(String authToken) {
        return true;
    }
}
