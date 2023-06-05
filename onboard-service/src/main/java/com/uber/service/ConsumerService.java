package com.uber.service;

import com.uber.common.exception.ApplicationException;
import com.uber.common.model.DocumentType;
import com.uber.config.exception.OnboardException;
import com.uber.entity.document.Document;
import com.uber.entity.onboarding.AuditOnboard;
import com.uber.entity.onboarding.AuditOnboardDetails;
import com.uber.entity.onboarding.DocumentStatus;
import com.uber.entity.onboarding.OnboardStatus;
import com.uber.entity.user.Driver;
import com.uber.entity.user.DriverDocument;
import com.uber.repository.audit.AuditOnboardJpaRepository;
import com.uber.repository.user.DriverDocumentsJpaRepository;
import com.uber.repository.user.DriverJpaRepository;
import com.uber.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
/**
 * @Author susmitha.v
 * listens onboarding request
 */
public class ConsumerService {

    @Autowired
    private DriverJpaRepository driverJpaRepository;

    @Autowired
    private AuditOnboardJpaRepository auditOnboardJpaRepository;

    @Autowired
    private DriverDocumentsJpaRepository driverDocumentsJpaRepository;
    @Autowired
    private DocumentVerificationClient client;

    private static final String UPDATED_BY = "onboarding-service";

    @KafkaListener(topics = "${onboarding.topic}", groupId = "onboard-driver")
    public void consume(String msg) {
        log.info("received message {}", msg);
        submitDocuments(msg);
        log.info("submitted documents for verification");

    }

    @Transactional
    private void submitDocuments(String mobile) {

        Driver driver = driverJpaRepository.findByMobile(mobile).get();
        List<DriverDocument> documents = driverDocumentsJpaRepository.findByDriverId(driver.getId());


        AuditOnboard auditOnboard = auditOnboardJpaRepository.save(getAuditOnboard(driver, documents));


        Map<DocumentType, File> validateDocuments = new HashMap<>();

        for (DriverDocument driverDocument : documents) {
            Document document = driverDocument.getDocument();
            File file = FileUtils.getFileFromBlob(document.getContent(), document.getFileName());
            validateDocuments.put(driverDocument.getType(), file);
        }


        String details = "submitting request";
        DocumentStatus status = DocumentStatus.PENDING_VALIDATION;

        try {
            DocumentValidationUtil.validate(validateDocuments);
            details = client.submitDocuments(auditOnboard.getId(), driver.getFirstName(), driver.getMobile(), validateDocuments);
            status = DocumentStatus.IN_VALIDATION;
            auditOnboard.setStatus(OnboardStatus.INITIATED);
            auditOnboard.setDetails(details);
        } catch (OnboardException ex) {
            details = "submission failed";
            log.error("submission failed");
            auditOnboard.setStatus(OnboardStatus.INITIATION_FAILURE);
            auditOnboard.setDetails(details);
        } catch (ApplicationException ex) {
            details = "validation failure";
            auditOnboard.setStatus(OnboardStatus.INITIATION_FAILURE);
            auditOnboard.setDetails(ex.getErrors().getErrorCode() + ex.getErrors().getErrorMessage());
            log.error("submission failed");
        }

        List<AuditOnboardDetails> auditOnboardDetailsList = auditOnboard.getAuditOnboardDetails();
        for (AuditOnboardDetails auditOnboardDetails :
                auditOnboardDetailsList) {
            auditOnboardDetails.setStatus(status);
        }

        auditOnboard.setAuditOnboardDetails(auditOnboardDetailsList);
        auditOnboardJpaRepository.save(auditOnboard);

    }

    private AuditOnboard getAuditOnboard(Driver driver, List<DriverDocument> documents) {
        AuditOnboard auditOnboard = new AuditOnboard();
        auditOnboard.setUpdatedBy(UPDATED_BY);
        auditOnboard.setDriver(driver);

        List<AuditOnboardDetails> auditOnboardDetails = new ArrayList<>();
        for (DriverDocument driverDocument : documents) {

            AuditOnboardDetails details = new AuditOnboardDetails();
            details.setUpdatedBy(UPDATED_BY);
            details.setDocument(driverDocument);
            details.setStatus(DocumentStatus.PENDING_VALIDATION);
            auditOnboardDetails.add(details);

        }
        auditOnboard.setAuditOnboardDetails(auditOnboardDetails);

        return auditOnboard;
    }

}
