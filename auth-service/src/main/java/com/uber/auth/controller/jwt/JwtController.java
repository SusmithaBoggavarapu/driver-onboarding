package com.uber.auth.controller.jwt;

import com.uber.auth.entity.jwt.JwtRequest;
import com.uber.auth.entity.jwt.JwtResponse;
import com.uber.auth.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Slf4j
public class JwtController {

    @Autowired
    private JwtService jwtService;

    @PostMapping({"/user/authenticate"})
    public JwtResponse generateJwtToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        return jwtService.generateJwtToken(jwtRequest);
    }
}
