package com.uber.entity.user;

import com.uber.entity.document.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.sql.Timestamp;

@Data
@Entity(name = "driver_documents")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverVehicleDocuments {

    @Id
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "driver_id")
    private Driver driver;

    @ManyToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(name = "document_id")
    private Document document;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;
}