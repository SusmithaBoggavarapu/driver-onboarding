package com.uber.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.OnboardServiceApplication;
import com.uber.common.model.DocumentType;
import com.uber.entity.document.Document;
import com.uber.entity.user.Driver;
import com.uber.entity.user.DriverDocument;
import com.uber.repository.audit.AuditOnboardJpaRepository;
import com.uber.repository.user.DriverDocumentsJpaRepository;
import com.uber.repository.user.DriverJpaRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@TestPropertySource(locations = "classpath:application.properties")
@AutoConfigureMockRestServiceServer
@RunWith(SpringRunner.class)
@SpringBootTest(classes= OnboardServiceApplication.class)
public class ConsumerServiceTest {

    @Autowired
    private DriverJpaRepository driverJpaRepository;

    @Autowired
    private AuditOnboardJpaRepository auditOnboardJpaRepository;

    @Autowired
    private DriverDocumentsJpaRepository driverDocumentsJpaRepository;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private RestTemplate restTemplate;


    private static final String TEST_DIR = "src/test/resources/";
    private static final String DRIVER_FILE_NAME = "driver_dao.json";

    @Before
    public void setUp() {
        server= MockRestServiceServer.createServer(restTemplate);
    }


    @Test
    public void consume() {
        createDriverAndDocuments();

        server.expect(requestTo("http://localhost:8081/"))
                .andRespond(withSuccess("successBody", MediaType.APPLICATION_JSON));

        consumerService.consume("9866968355");
    }

    public void createDriverAndDocuments() {
        Driver driver = getDriver();
        driverJpaRepository.save(driver);
        System.out.println(driverJpaRepository.findAll());

        DriverDocument pan = DriverDocument.builder().driver(driver).document(getDocument()).type(DocumentType.PAN).isActive(true).build();
        DriverDocument rc = DriverDocument.builder().driver(driver).document(getDocument()).type(DocumentType.VEHICLE_RC).isActive(true).build();
        driverDocumentsJpaRepository.save(pan);
        driverDocumentsJpaRepository.save(rc);

        System.out.println(driverDocumentsJpaRepository.findAll());
    }

    private Driver getDriver() {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(new File(TEST_DIR + DRIVER_FILE_NAME), Driver.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Document getDocument() {
        try {
            Blob blob = new SerialBlob(new FileInputStream(TEST_DIR + DRIVER_FILE_NAME).readAllBytes());
            return Document.builder().content(blob).fileName(DRIVER_FILE_NAME).build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SerialException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
