package com.uber.service;

import com.uber.entity.user.Address;
import com.uber.entity.user.Driver;
import com.uber.repository.user.DriverJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class DriverServiceTest {

    @InjectMocks
    private DriverService driverService;

    @Mock
    private DriverJpaRepository driverJpaRepository;

    @Test
    void getDriverDetails() {

        Driver expectedDriver = Driver.builder().address(Address.builder().city("city").pincode(502300).state("telangana").build()).email("test@walmart.com").firstName("test").lastName("walmart").mobile("12345").build();
        Mockito.when(driverJpaRepository.findById(12345)).thenReturn(Optional.of(expectedDriver));
        assertEquals(expectedDriver, driverService.getDriverDetails("12345"));
    }

    @Test
    void saveDriverDetails() {

        Driver expectedDriver = Driver.builder().address(Address.builder().city("city").pincode(502300).state("telangana").build()).email("test@walmart.com").firstName("test").lastName("walmart").mobile("12345").build();
        Mockito.when(driverJpaRepository.save(expectedDriver)).thenReturn(expectedDriver);
        assertEquals(expectedDriver, driverService.saveDriverDetails(expectedDriver));

    }

    @Test
    void saveDriverDetailsException() {

        Driver driver = Driver.builder().address(Address.builder().city("city").pincode(502300).state("telangana").build()).email("test@walmart.com").firstName("test").lastName("walmart").build();
        Mockito.when(driverJpaRepository.save(driver)).thenReturn(driver);
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                driverService.saveDriverDetails(driver)
        );
    }

    @Test
    void updateDriverDetails() {

        Driver expectedDriver = Driver.builder().address(Address.builder().city("city").pincode(502300).state("telangana").build()).email("test@walmart.com").firstName("test").lastName("walmart").mobile("12345").build();
        Mockito.when(driverJpaRepository.findByMobile("12345")).thenReturn(Optional.of(expectedDriver));
        Mockito.when(driverJpaRepository.save(expectedDriver)).thenReturn(expectedDriver);
        driverService.updateDriverDetails(expectedDriver);
        assertEquals(expectedDriver, driverService.updateDriverDetails(expectedDriver));
    }

    @Test
    void updateDriverDetailsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> driverService.updateDriverDetails(null));
    }

}
