package com.uber.repository.user;

import com.uber.entity.user.DriverVehicleDocuments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverVehicleDocumentJpaRepository extends JpaRepository<DriverVehicleDocuments, String> {
}