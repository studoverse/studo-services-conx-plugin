package com.studo.services.attendance.entity.identity;

import com.studo.services.attendance.entity.CoEntity;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_IDENTITAETEN_V", schema = "TUG_NEW")
public class IdentityEntity extends CoEntity {

    @Column(name = "PERSON_NR")
    public BigDecimal staffId;

    @Column(name = "ST_PERSON_NR")
    public BigDecimal studentId;

    @Column(name = "NR_OBFUSCATED")
    public String obfuscatedId;
}
