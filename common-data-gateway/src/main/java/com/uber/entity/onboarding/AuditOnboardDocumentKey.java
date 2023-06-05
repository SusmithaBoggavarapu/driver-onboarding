package com.uber.entity.onboarding;

import com.uber.entity.user.DriverDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditOnboardDocumentKey implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private AuditOnboard auditOnboard;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private DriverDocument document;

}
