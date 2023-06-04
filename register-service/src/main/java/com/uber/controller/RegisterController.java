package com.uber.controller;

import com.uber.entity.DriverDto;
import com.uber.common.response.Response;
import com.uber.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = {"register"})
public class RegisterController extends BaseController{

    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<Response> registerDriver(@RequestBody DriverDto driverDto) {
        log.info("driver details {}", driverDto);
        registerService.registerDriverAndAuthenticate(driverDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Response.builder().status("SUCCESS").payload(driverDto).build());
    }

    @PutMapping
    public ResponseEntity<Response> updateDriver(@RequestBody DriverDto driverDto) {
        log.info("driver details {}", driverDto);
        registerService.updateDriverAndAuthenticate(driverDto);
        return ResponseEntity.ok(Response.builder().status("SUCCESS").payload(driverDto).build());
    }

}
