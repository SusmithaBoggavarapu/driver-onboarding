package com.uber.entity.onboarding;

import com.uber.entity.document.Document;
import com.uber.entity.user.Driver;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity(name = "audit_onboard")
@EntityListeners(UUIDListener.class)
public class OnboardTracking {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id")
    private Document document;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OnboardStatus onboardStatus;

    @Column(name = "details")
    private String details;

    @Column(name = "updated_on", insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

}


