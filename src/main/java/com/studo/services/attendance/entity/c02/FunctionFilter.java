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
@Table(name = "CO_LOC_ATTENDANCE_FUNCTION")
@Deprecated
public class FunctionFilter extends PanacheEntity {
    @Column(name = "FUNCTION_TYPE")
    public String functionType;
}
