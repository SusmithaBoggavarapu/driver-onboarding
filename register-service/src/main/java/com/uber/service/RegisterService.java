package com.uber.service;

import com.uber.entity.DriverDto;
import com.uber.entity.user.Address;
import com.uber.entity.user.Driver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegisterService {

    @Autowired
    private DriverService driverService;

    private static final String CREATED_BY = "register-service";


    public void registerDriver(DriverDto driverDto) {
        Driver driver = convertToEntity(driverDto);
        driver = driverService.saveDriverDetails(driver);
        log.info("driver registered successfully {}", driver);
    }

    public void updateDriver(DriverDto driverDto) {
        Driver driver = convertToEntity(driverDto);
        driver = driverService.updateDriverDetails(driver);
        log.info("driver registered successfully {}", driver);
    }


    private Driver convertToEntity(DriverDto driverDto) {
        DriverDto.Address addressDto = driverDto.getAddress();
        Address address = Address.builder().city(addressDto.getCity()).state(addressDto.getState()).pincode(addressDto.getPincode()).createdBy(CREATED_BY).updatedBy(CREATED_BY).build();
        return Driver.builder().address(address).firstName(driverDto.getFirstName()).lastName(driverDto.getLastName()).email(driverDto.getEmail()).mobile(driverDto.getMobile()).createdBy(CREATED_BY).updatedBy(CREATED_BY).build();
    }
}
