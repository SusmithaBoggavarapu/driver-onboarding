package com.uber.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

@Builder
@Data
@Entity(name = "address")
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pincode")
    private int pincode;

    @Column(name = "created_on", insertable = false)
    private Timestamp createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on", insertable = false)
    private Timestamp updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;
}