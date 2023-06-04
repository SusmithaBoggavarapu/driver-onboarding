package com.uber.service;

import com.uber.client.AuthClient;
import com.uber.common.exception.BadRequestException;
import com.uber.common.exception.Errors;
import com.uber.entity.AuthenticationResponse;
import com.uber.entity.DriverDto;
import com.uber.entity.user.Address;
import com.uber.entity.user.Driver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class RegisterService {

    @Autowired
    private DriverService driverService;


    @Autowired
    private AuthClient authClient;


    public void registerDriverAndAuthenticate(DriverDto driverDto) {
        try {
            driverService.getDriverDetails(driverDto.getMobile());
            throw new BadRequestException(Errors.DUPLICATE_USER);
        } catch (NoSuchElementException ex) {
            log.info("user does not exist, registering driver {}", driverDto);
        }

        registerDriver(driverDto);
        AuthenticationResponse response = authClient.authenticate(driverDto.getMobile(), driverDto.getPassword());
        driverDto.setAuthToken(response.getJwtToken());
    }

    public void registerDriver(DriverDto driverDto) {
        Driver driver = convertToEntity(driverDto);
        driver = driverService.saveDriverDetails(driver);
        log.info("driver registered successfully {}", driver);
    }

    public void updateDriverAndAuthenticate(DriverDto driverDto) {
        updateDriver(driverDto);
        AuthenticationResponse response = authClient.authenticate(driverDto.getMobile(), driverDto.getPassword());
        driverDto.setAuthToken(response.getJwtToken());
    }

    public void updateDriver(DriverDto driverDto) {
        Driver driver = convertToEntity(driverDto);
        driver = driverService.updateDriverDetails(driver);
        log.info("driver registered successfully {}", driver);
    }


    private Driver convertToEntity(DriverDto driverDto) {
        DriverDto.Address addressDto = driverDto.getAddress();
        Address address = Address.builder().city(addressDto.getCity()).state(addressDto.getState()).pincode(addressDto.getPincode()).build();
        return Driver.builder().address(address).firstName(driverDto.getFirstName()).lastName(driverDto.getLastName()).email(driverDto.getEmail()).mobile(driverDto.getMobile()).build();
    }
}
