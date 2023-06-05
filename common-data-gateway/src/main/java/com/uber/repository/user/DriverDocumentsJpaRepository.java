package com.uber.repository.user;

import com.uber.common.model.DocumentType;
import com.uber.entity.user.DriverDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverDocumentsJpaRepository extends JpaRepository<DriverDocument, Integer> {
    List<DriverDocument> findByDriverId(int driverId);

    DriverDocument findByDriverIdAndType(int driverId, DocumentType documentType);
}