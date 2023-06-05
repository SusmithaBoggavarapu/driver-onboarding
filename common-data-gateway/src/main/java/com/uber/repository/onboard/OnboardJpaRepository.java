package com.uber.repository.onboard;

import com.uber.entity.onboarding.AuditOnboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardJpaRepository extends JpaRepository<AuditOnboard, String> {
}