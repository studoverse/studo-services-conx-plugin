package com.studo.services.attendance.entity.course;

import com.studo.services.attendance.entity.organisation.OrganisationEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_LV_ERHEBUNG_V", schema = "TUG_NEW")
public class CourseEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @Column(name = "STP_SP_NR")
    public BigDecimal id;

    @Column(name = "STP_SP_LVNR")
    public String courseCode;

    @Column(name = "SJ_NAME")
    public String academicYear;

    @Column(name = "SEMESTER_KB")
    public String semester;

    @Column(name = "STP_SP_TITEL")
    public String title;

    @Column(name = "STP_LV_ART_NAME")
    public String type;

    @Column(name = "LV_STATUS_ALLE")
    public String allStates;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORG_NR_BETREUT", referencedColumnName = "NR")
    public OrganisationEntity organisationEntity;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "courseEntity")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<CourseGroupEntity> courseGroupEntities;
}
