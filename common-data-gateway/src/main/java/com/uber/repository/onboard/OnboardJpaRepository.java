package com.uber.repository.onboard;

import com.uber.entity.onboarding.OnboardTracking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardJpaRepository extends JpaRepository<OnboardTracking, String> {
}