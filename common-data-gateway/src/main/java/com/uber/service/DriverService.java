package com.uber.service;

import com.uber.entity.user.Address;
import com.uber.entity.user.Driver;
import com.uber.repository.user.DriverJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Slf4j
public class DriverService {

    @Autowired
    private DriverJpaRepository driverJpaRepository;
    public Driver getDriverDetails(String mobile) {
        return driverJpaRepository.findByMobile(mobile).    get();
    }

    @Transactional
    public Driver saveDriverDetails(Driver driver) {
        log.info("saving driver details {} ", driver);
        if (Objects.isNull(driver) || StringUtils.isBlank(driver.getMobile()))
            throw new IllegalArgumentException("invalid driver details");
        driver = driverJpaRepository.save(driver);
        log.info("driver with mobile: {} saved successfully", driver.getMobile());
        return driver;
    }

    @Transactional
    public Driver updateDriverDetails(Driver driver) {

        if (Objects.isNull(driver) || StringUtils.isBlank(driver.getMobile()))
            throw new IllegalArgumentException("invalid driver details");

        Driver driverFromDB = driverJpaRepository.findByMobile(driver.getMobile()).orElseThrow();
        driverFromDB.setFirstName(driver.getFirstName());
        driverFromDB.setLastName(driver.getLastName());
        driverFromDB.setEmail(driver.getLastName());
        if(Objects.nonNull(driver.getAddress())){
            Address address = driver.getAddress();
            Address addressFromDB = driverFromDB.getAddress();
            addressFromDB.setCity(address.getCity());
            addressFromDB.setPincode(address.getPincode());
            addressFromDB.setState(address.getState());
        }

        driverJpaRepository.save(driverFromDB);
        log.info("driver {} updated successfully", driver);
        return driver;

    }


}
