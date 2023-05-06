package com.uber.entity.onboarding;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.sql.Timestamp;

@Data
@Entity(name = "onboard_tracking")
public class OnboardTracking {

    @EmbeddedId
    private DriverKey driver;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OnboardStatus onboardStatus;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

}

