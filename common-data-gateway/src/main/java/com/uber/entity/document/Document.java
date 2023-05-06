package com.uber.entity.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Blob;
import java.sql.Timestamp;

@Data
@Entity(name = "document")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "content")
    private Blob content;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "created_by")
    private String createdBy;
}
