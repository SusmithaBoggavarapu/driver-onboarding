package com.uber.service;

import com.uber.common.model.DocumentType;
import com.uber.entity.user.Address;
import com.uber.entity.user.Driver;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.File;


@DataJpaTest
public class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;

    @Mock
    private DriverService driverService;

    private static String FILE_NAME = "src/test/resources/document/vehicle_insurance.pdf";

    @Test
    public void saveDocumentTest() {

        Driver driver = Driver.builder().address(Address.builder().city("city").pincode(502300).state("telangana").build()).email("test@walmart.com").firstName("test").lastName("walmart").mobile("12345").build();
        Mockito.when(driverService.getDriverDetails(Mockito.anyString())).thenReturn(driver);
        documentService.saveDocument("12345", new File(FILE_NAME), DocumentType.PAN);
     //   log.info(repository.findAll().toString());
    }


}
