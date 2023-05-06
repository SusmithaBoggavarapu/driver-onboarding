package com.uber.service;

import com.uber.entity.user.Address;
import com.uber.entity.user.Driver;
import com.uber.entity.vehicle.DriverVehicles;
import com.uber.entity.vehicle.Vehicle;
import com.uber.repository.user.DriverVehicleDocumentJpaRepository;
import com.uber.repository.user.DriverVehiclesJpaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;

    @Mock
    private DriverService driverService;

    @Mock
    private DriverVehicleDocumentJpaRepository driverVehicleDocumentJpaRepository;

    @Mock
    private DriverVehiclesJpaRepository driverVehiclesJpaRepository;

    private static String FILE_NAME = "src/test/resources/document/vehicle_insurance.pdf";

    @Test
    void getDriverDetails() {

        Driver driver = Driver.builder().address(Address.builder().city("city").pincode(502300).state("telangana").build()).email("test@walmart.com").firstName("test").lastName("walmart").mobile("12345").build();
        Mockito.when(driverService.getDriverDetails("12345")).thenReturn(driver);
        Vehicle vehicle = Vehicle.builder().createdBy("created_by").model("honda").type("sedan").build();
        DriverVehicles driverVehicles = DriverVehicles.builder().driver(driver).vehicle(vehicle).build();
        Mockito.when(driverVehiclesJpaRepository.findByDriverId(Mockito.anyInt())).thenReturn(driverVehicles);
        documentService.saveDocument("12345", new File(FILE_NAME));
        Mockito.verify(driverVehiclesJpaRepository, Mockito.times(1)).findByDriverId(Mockito.anyInt());

    }


}
