package com.studo.services.attendance.entity.function;

import com.studo.services.attendance.entity.staff.StaffFunctionCompositeKey;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_PERS_ORG_FUNK_DE_FUNKTYP_V", schema = "TUG_NEW")
public class FunctionEntity extends PanacheEntityBase {

    @EmbeddedId
    private StaffFunctionCompositeKey id;

    @Column(name = "ORG_NR", insertable=false, updatable=false)
    public BigDecimal organisationId;

    @Column(name = "PERSON_NR", insertable = false, updatable = false)
    public BigDecimal staffId;

    @Column(name = "NAME")
    public String name;

    @Column(name = "KURZBEZEICHNUNG")
    public String type;

    @Column(name = "ANZEIGE_IN_PERS_LISTE")
    public String showInPersList; // "J" or "N"

    @Column(name = "BEGINN_DATUM")
    public Date startDate;

    @Column(name = "ENDE_DATUM")
    public Date endDate; // Optional

    public String getId() {
        return id.toString();
    }
}
