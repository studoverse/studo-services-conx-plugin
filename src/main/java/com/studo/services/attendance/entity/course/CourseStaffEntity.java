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
@Table(name = "PU_LV_GRP_LEHRENDE_V", schema = "TUG_NEW")
public class CourseStaffEntity extends PanacheEntityBase {

    @EmbeddedId
    public CourseStaffComposedKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LV_GRP_NR", referencedColumnName = "LV_GRP_NR", insertable=false, updatable=false)
    public CourseGroupEntity courseGroupEntity;

    @Column(name = "PERSON_NR", insertable=false, updatable=false)
    public BigDecimal staffId;

    @Column(name = "LA_P_FUNK_NAME")
    public String name;
}
