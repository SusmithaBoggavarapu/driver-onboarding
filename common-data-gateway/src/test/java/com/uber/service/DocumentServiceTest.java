package com.uber.service;

import com.uber.common.model.DocumentType;
import com.uber.entity.user.Address;
import com.uber.entity.user.Driver;
import com.uber.entity.user.DriverDocument;
import com.uber.repository.user.DriverDocumentsJpaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.io.File;


@DataJpaTest
public class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;

    @Mock
    private DriverService driverService;

    @Mock
    private DriverDocumentsJpaRepository driverDocumentsJpaRepository;

    private static String FILE_NAME = "src/test/resources/document/vehicle_insurance.pdf";

    @Test
    public void saveDocumentTest() {

        Driver driver = Driver.builder().address(Address.builder().city("city").pincode(502300).state("telangana").build()).email("test@walmart.com").firstName("test").lastName("walmart").mobile("12345").build();
        Mockito.when(driverService.getDriverDetails(Mockito.anyString())).thenReturn(driver);
        Mockito.when(driverDocumentsJpaRepository.findByDriverIdAndType(Mockito.anyInt(), Mockito.any())).thenReturn(new DriverDocument());
        documentService.saveDocument("12345", new File(FILE_NAME), DocumentType.PAN);
    }


}
