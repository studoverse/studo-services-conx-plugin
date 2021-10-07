package com.studo.services.attendance.entity.c02;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "CO_LOC_COURSE_FILTER")
public class CourseFilter extends PanacheEntity {

    @Column(name = "ACADEMIC_YEAR")
    public String academicYear;

    @Column(name = "SEMESTER_W")
    public String semesterW;

    @Column(name = "SEMESTER_S")
    public String semesterS;
}
