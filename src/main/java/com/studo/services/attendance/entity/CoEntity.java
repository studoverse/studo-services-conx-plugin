package com.studo.services.attendance.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@MappedSuperclass
public abstract class CoEntity extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @Column(name = "NR")
    public BigDecimal id;

}
