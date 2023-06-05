package com.uber.service;

import com.uber.entity.DocumentValidationStatus;
import com.uber.entity.onboarding.AuditOnboard;
import com.uber.entity.onboarding.AuditOnboardDetails;
import com.uber.entity.onboarding.DocumentStatus;
import com.uber.entity.onboarding.OnboardStatus;
import com.uber.entity.onboarding.track.OnboardTrackStatus;
import com.uber.entity.onboarding.track.TrackOnboard;
import com.uber.repository.audit.AuditOnboardJpaRepository;
import com.uber.repository.onboard.track.TrackOnboardJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnboardStatusService {

    @Autowired
    private AuditOnboardJpaRepository auditOnboardJpaRepository;

    @Autowired
    private TrackOnboardJpaRepository trackOnboardJpaRepository;

    public void onboardAndSendTrackingDevice(DocumentValidationStatus status) {
        if (onboard(status)) {
            AuditOnboard auditOnboard = auditOnboardJpaRepository.findById(status.getRequestId()).get();
            TrackOnboard trackOnboard = new TrackOnboard();
            if (trackOnboardJpaRepository.findById(auditOnboard.getDriver().getId()).isPresent()) {
                trackOnboard = trackOnboardJpaRepository.findById(auditOnboard.getDriver().getId()).get();
            } else {
                trackOnboard.setDriverId(auditOnboard.getDriver().getId());
            }

            trackOnboard.setStatus(OnboardTrackStatus.TRACK_DEVICE_SENT);
            trackOnboardJpaRepository.save(trackOnboard);
        }
    }

    public boolean onboard(DocumentValidationStatus status) {

        AuditOnboard auditOnboard = auditOnboardJpaRepository.findById(status.getRequestId()).get();
        List<AuditOnboardDetails> auditOnboardDetailsList = auditOnboard.getAuditOnboardDetails();

        List<DocumentValidationStatus.ValidationResponseStatus> statusList = status.getDocumentStatus();

        int failCnt = 0;
        for (AuditOnboardDetails details : auditOnboardDetailsList) {
            for (DocumentValidationStatus.ValidationResponseStatus validationStatus : statusList) {
                if (validationStatus.getType().equalsIgnoreCase(details.getDocument().getType().name())) {
                    if (validationStatus.isValid()) {
                        details.setStatus(DocumentStatus.VALID);
                    } else {
                        failCnt++;
                        details.setStatus(DocumentStatus.INVALID);
                    }
                }
            }
        }
        auditOnboard.setStatus(failCnt == 0 ? OnboardStatus.SUCCESS : failCnt < DocumentStatus.values().length ?
                OnboardStatus.PARTIAL_FAILURE : OnboardStatus.FAILURE);

        auditOnboardJpaRepository.save(auditOnboard);
        return failCnt == 0;
    }


}
