package com.uber.controller;

import com.uber.entity.DriverDto;
import com.uber.entity.common.Response;
import com.uber.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = {"register"})
public class RegisterController extends BaseController {


    @Autowired
    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<Response<String>> registerDriver(@RequestBody DriverDto driverDto) {
        log.info("driver details {}", driverDto);
        registerService.registerDriver(driverDto);
        return ResponseEntity.ok(new Response<>());
    }

    @PutMapping
    public ResponseEntity<Response<String>> updateDriver(@RequestBody DriverDto driverDto) {
        log.info("driver details {}", driverDto);
        registerService.updateDriver(driverDto);
        return ResponseEntity.ok(new Response<>());
    }

}
