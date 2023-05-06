package com.uber.repository.document;

import com.uber.entity.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentJpaRepository extends JpaRepository<Document, String> {
}