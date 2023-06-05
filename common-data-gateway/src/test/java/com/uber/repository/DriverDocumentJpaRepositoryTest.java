package com.uber.repository;

import com.uber.TestDataUtil;
import com.uber.common.model.DocumentType;
import com.uber.entity.document.Document;
import com.uber.entity.user.Driver;
import com.uber.entity.user.DriverDocument;
import com.uber.repository.user.DriverDocumentsJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Slf4j
public class DriverDocumentJpaRepositoryTest {
    @Autowired
    DriverDocumentsJpaRepository repository;

    @Test
    public void save(){
        Driver driver = TestDataUtil.getDriver();
        Document document = TestDataUtil.getDocument();
        DriverDocument driverDocuments = DriverDocument.builder().driver(driver).document(document).type(DocumentType.PAN).isActive(true).build();
       repository.save(driverDocuments);

    }
}