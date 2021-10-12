package com.studo.services.attendance.entity.staff;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Embeddable
public class StaffFunctionCompositeKey implements Serializable {

    @Column(name = "FUNK_DE_NR")
    private BigDecimal functionId;

    @Column(name = "ORG_NR")
    private BigDecimal organisationId;

    @Column(name = "PERSON_NR")
    private BigDecimal staffId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffFunctionCompositeKey that = (StaffFunctionCompositeKey) o;
        return Objects.equals(functionId, that.functionId) && Objects.equals(organisationId, that.organisationId) && Objects.equals(staffId, that.staffId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(functionId, organisationId, staffId);
    }
}
