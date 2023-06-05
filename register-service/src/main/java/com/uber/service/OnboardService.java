package com.uber.service;

import com.uber.common.exception.BadRequestException;
import com.uber.common.exception.Errors;
import com.uber.common.model.DocumentType;
import com.uber.entity.onboarding.track.OnboardTrackStatus;
import com.uber.entity.onboarding.track.TrackOnboard;
import com.uber.entity.user.Driver;
import com.uber.entity.user.DriverDocument;
import com.uber.repository.onboard.track.TrackOnboardJpaRepository;
import com.uber.repository.user.DriverDocumentsJpaRepository;
import com.uber.repository.user.DriverJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

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

    @Autowired
    private TrackOnboardJpaRepository trackOnboardJpaRepository;

    public void triggerOnboarding(Driver driver) {

        List<DocumentType> allDocumentTypes = new ArrayList<>();
        List<DriverDocument> driverDocuments = driverDocumentsJpaRepository.findByDriverId(driver.getId());
        driverDocuments.stream().filter(driverDocument -> driverDocument.getIsActive()).forEach(driverDocument -> allDocumentTypes.add(driverDocument.getType()));

        List<DocumentType> mandatoryDocuments = new LinkedList<>(Arrays.asList(DocumentType.values()));

        if (allDocumentTypes.containsAll(mandatoryDocuments))
            producerService.sendMessage(onboardingTopic, driver.getMobile());
        else {

            mandatoryDocuments.removeAll(allDocumentTypes);
            throw new BadRequestException(Errors.MISSING_DOCUMENTS, mandatoryDocuments.toString());
        }
    }

    public void triggerOnboarding(String mobile) {
        triggerOnboarding(driverJpaRepository.findByMobile(mobile).get());
    }

    public void activateDriver(String mobile) {
        Driver driver = driverJpaRepository.findByMobile(mobile).get();
        try {
            TrackOnboard onboard = trackOnboardJpaRepository.findById(driver.getId()).get();
            onboard.setOnboardedOn(new Timestamp(System.currentTimeMillis()));
            onboard.setStatus(OnboardTrackStatus.ONBOARDED);
            trackOnboardJpaRepository.save(onboard);
        } catch (NoSuchElementException ex) {
            throw new BadRequestException(Errors.INVALID_ONBOARD_REQUEST);
        }

    }


}
