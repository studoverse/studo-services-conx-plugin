package com.studo.services.attendance.entity.course;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_LV_GRUPPEN_V", schema = "TUG_NEW")
public class CourseGroupEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @Column(name = "LV_GRP_NR")
    public BigDecimal id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "STP_SP_NR", referencedColumnName = "STP_SP_NR")
    public CourseEntity courseEntity;

    @Column(name = "NAME")
    public String name;

    @Column(name = "GELOESCHT_FLAG")
    public String deleted;

    @Column(name = "STANDARDGRUPPE_FLAG")
    public String isStandard;

    @Column(name = "ANMELDE_BEGINN")
    public Date registrationStart;

    @Column(name = "ANMELDE_ENDE")
    public Date registrationEnd;

    @OneToMany(mappedBy = "courseGroupEntity")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 999)
    public List<CourseStaffEntity> courseStaffEntities;

    @OneToMany(mappedBy = "courseGroupEntity")
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 999)
    public List<CourseStudentEntity> courseStudentEntities;

    @OneToMany(mappedBy = "courseGroupEntity")
    @BatchSize(size = 999)
    public List<CourseEventEntity> courseEventEntities;

}
