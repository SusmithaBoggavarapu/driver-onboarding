package com.uber.repository.onboard.track;

import com.uber.entity.onboarding.track.TrackOnboard;
import com.uber.entity.user.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackOnboardJpaRepository extends JpaRepository<TrackOnboard, Integer> {
}