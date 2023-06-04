package com.uber.service;

import com.uber.common.exception.ApplicationException;
import com.uber.common.exception.Errors;
import com.uber.common.model.DocumentType;
import com.uber.entity.user.Driver;
import com.uber.entity.user.DriverDocument;
import com.uber.repository.user.DriverDocumentsJpaRepository;
import com.uber.repository.user.DriverJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class OnboardService {
    @Value("${onboarding.topic}")
    private String onboardingTopic;
    @Autowired
    private ProducerService producerService;

    @Autowired
    private DriverDocumentsJpaRepository driverDocumentsJpaRepository;

    @Autowired
    private DriverJpaRepository driverJpaRepository;

    public void triggerOnboarding(Driver driver) {

        List<DocumentType> allDocumentTypes = new ArrayList<>();
        List<DriverDocument> driverDocuments = driverDocumentsJpaRepository.findByDriverId(driver.getId());
        driverDocuments.stream().filter(driverDocument -> driverDocument.getIsActive()).forEach(driverDocument -> allDocumentTypes.add(driverDocument.getType()));

        if (allDocumentTypes.containsAll(Arrays.asList(DocumentType.values())))
            producerService.sendMessage(onboardingTopic, driver.getMobile());
        else {
            throw new ApplicationException(Errors.MISSING_DOCUMENTS);
        }
    }

    public void triggerOnboarding(int driverId) {
        triggerOnboarding(driverJpaRepository.findById(driverId).get());
    }

    public void triggerOnboarding(String mobile) {
        triggerOnboarding(driverJpaRepository.findByMobile(mobile).get());
    }

}
