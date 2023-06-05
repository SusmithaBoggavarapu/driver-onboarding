package com.uber.entity.onboarding.track;

import com.uber.entity.user.Driver;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.sql.Timestamp;

@Data
@Entity(name = "track_onboard")
public class TrackOnboard {

    @Id
    @Column(name = "driver_id")
    private int driverId;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name="driver_id")
    private Driver driver;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OnboardTrackStatus status;

    @Column(name = "onboarded_on")
    private Timestamp onboardedOn;

}


