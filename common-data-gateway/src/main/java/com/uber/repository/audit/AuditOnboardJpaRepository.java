package com.uber.repository.audit;

import com.uber.entity.onboarding.AuditOnboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditOnboardJpaRepository extends JpaRepository<AuditOnboard, String> {
}