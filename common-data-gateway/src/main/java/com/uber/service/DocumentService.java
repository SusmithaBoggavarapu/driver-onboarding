package com.uber.service;

import com.uber.common.exception.Errors;
import com.uber.common.exception.NoContentException;
import com.uber.common.model.DocumentType;
import com.uber.entity.document.Document;
import com.uber.entity.user.Driver;
import com.uber.entity.user.DriverDocument;
import com.uber.repository.user.DriverDocumentsJpaRepository;
import com.uber.util.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class DocumentService {

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverDocumentsJpaRepository repository;

    private static final String TMP_FOLDER = "/tmp/uploads/";

    public void saveDocument(String mobile, File file, DocumentType type) {
        Blob blob = FileUtils.getBlob(file);
        Document document = Document.builder().content(blob).fileName(file.getName()).build();
        Driver driver = driverService.getDriverDetails(mobile);
        DriverDocument driverDocuments = repository.findByDriverIdAndType(driver.getId(), type);

        if (Objects.isNull(driverDocuments)) {
            driverDocuments = DriverDocument.builder().driver(driver).type(type).isActive(true).build();
        }

        driverDocuments.setDocument(document);
        repository.save(driverDocuments);

    }

    public File getDocument(String mobile, DocumentType type) {

        Driver driver = driverService.getDriverDetails(mobile);
        List<DriverDocument> driverDocuments = repository.findByDriverId(driver.getId());
        File file;
        try {
            DriverDocument driverDocument = driverDocuments.stream().filter(document -> type.equals(document.getType())).findAny().get();
            Blob blob = driverDocument.getDocument().getContent();
            file = new File(TMP_FOLDER + driverDocument.getDocument().getFileName());
            InputStream in = blob.getBinaryStream();
            OutputStream out = new FileOutputStream(file);
            IOUtils.copy(in, out);
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NoSuchElementException e) {
            throw new NoContentException(Errors.NOT_FOUND_DOCUMENT);
        }
        return file;

    }

}
