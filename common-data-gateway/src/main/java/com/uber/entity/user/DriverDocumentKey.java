//package com.uber.entity.user;
//
//import com.uber.entity.document.Document;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Embeddable;
//import javax.persistence.ManyToOne;
//import javax.persistence.PrimaryKeyJoinColumn;
//import java.io.Serializable;
//
//@Embeddable
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//public
//class DriverDocumentKey implements Serializable {
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn(name = "driver_id")
//    private Driver driver;
//
//    @ManyToOne(cascade = CascadeType.ALL)
//    @PrimaryKeyJoinColumn(name = "document_id")
//    private Document document;
//
//
//}