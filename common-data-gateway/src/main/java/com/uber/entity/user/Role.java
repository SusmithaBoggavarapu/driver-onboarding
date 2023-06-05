package com.uber.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Data
@Entity(name = "role")
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @Column(name = "name")
    private String roleName;
    @Column(name = "description")
    private String roleDescription;
}