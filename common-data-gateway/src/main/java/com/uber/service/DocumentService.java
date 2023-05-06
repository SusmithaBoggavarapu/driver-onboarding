package com.uber.service;

import com.uber.entity.document.Document;
import com.uber.entity.user.Driver;
import com.uber.entity.user.DriverVehicleDocuments;
import com.uber.repository.user.DriverVehicleDocumentJpaRepository;
import com.uber.repository.user.DriverVehiclesJpaRepository;
import com.uber.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.sql.Blob;

public class DocumentService {

    private final String CREATE_BY = "onboard_service";
    @Autowired
    private DriverVehicleDocumentJpaRepository driverVehicleDocumentJpaRepository;

    @Autowired
    private DriverVehiclesJpaRepository driverVehiclesJpaRepository;

    @Autowired
    private DriverService driverService;

    public void saveDocument(String mobile, File file) {
        Blob blob = FileUtils.getBlob(file);

        Document document = Document.builder().content(blob).createdBy(CREATE_BY).build();
        Driver driver = driverService.getDriverDetails(mobile);
        DriverVehicleDocuments driverVehicleDocuments = DriverVehicleDocuments.builder().driver(driver).document(document).vehicle(driverVehiclesJpaRepository.findByDriverId(driver.getId()).getVehicle()).build();
        driverVehicleDocumentJpaRepository.save(driverVehicleDocuments);


    }


}
