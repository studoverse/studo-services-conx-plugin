package com.studo.services.attendance.entity.student;

import com.studo.services.attendance.entity.CoEntity;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_ST_PERSONEN_V", schema = "STUD")
public class StudentEntity extends CoEntity {

    @Column(name = "FAMILIENNAME")
    public String lastName;

    @Column(name = "VORNAME")
    public String firstName;

    @Column(name = "MATRIKELNUMMER")
    public String matriculationNumber;

    @Column(name = "EMAIL_ADRESSE")
    public String email;

}
