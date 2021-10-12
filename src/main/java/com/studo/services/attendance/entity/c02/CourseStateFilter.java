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
@Table(name = "CO_LOC_COURSE_STATE_FILTER")
@Deprecated
public class CourseStateFilter extends PanacheEntity {
    @Column(name = "STATE_FILTER")
    public String stateFilter;
}
