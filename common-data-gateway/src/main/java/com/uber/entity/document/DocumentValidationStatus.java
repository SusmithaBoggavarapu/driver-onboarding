package com.uber.entity.document;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity(name = "document_validation_status")
public class DocumentValidationStatus {

    @EmbeddedId
    private DocumentKey document;

    @Column(name = "is_valid")
    private boolean isValid;

    @Column(name = "details")
    private String details;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

}

@Embeddable
class DocumentKey implements Serializable {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id")
    private Document document;

}
