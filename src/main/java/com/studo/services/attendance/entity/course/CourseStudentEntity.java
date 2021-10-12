package com.studo.services.attendance.entity.course;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_LV_TEILNEHMER_FIXPLATZ_V", schema = "TUG_NEW")
public class CourseStudentEntity extends PanacheEntityBase {

    @EmbeddedId
    public CourseStudentComposedKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STP_SP_NR", referencedColumnName = "STP_SP_NR", insertable=false, updatable=false)
    public CourseEntity courseEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LV_GRP_NR", referencedColumnName = "LV_GRP_NR", insertable=false, updatable=false)
    public CourseGroupEntity courseGroupEntity;

    @Column(name = "ST_PERSON_NR", insertable=false, updatable=false)
    public BigDecimal studentId;
}
