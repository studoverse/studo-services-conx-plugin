
package com.studo.services.attendance.entity.organisation;

import com.studo.services.attendance.entity.CoEntity;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_ORG_V", schema = "TUG_NEW")
public class OrganisationEntity extends CoEntity {

    @Column(name = "NAME")
    public String organizationName;

    @Column(name = "NAME_ENGL")
    public String organizationNameEn;

    @Column(name = "KENNUNG")
    public String identification;

    @Column(name = "ADRESSE")
    public String address;

    @Column(name = "GUELTIG_AB")
    public Date validFrom;

    @Column(name = "GUELTIG_BIS")
    public Date validTo;

    @Column(name = "ORG_NR")
    public BigDecimal organisationId;
}
