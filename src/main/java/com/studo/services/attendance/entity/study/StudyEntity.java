package com.studo.services.attendance.entity.study;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_STUDIEN_2_V", schema = "STUD")
public class StudyEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    @Column(name = "ST_STUDIUM_NR")
    public BigDecimal id;

    @Column(name = "ST_PERSON_NR")
    public BigDecimal studentId;

    @Column(name = "STUDBEZ")
    public String name;
}
