package com.studo.services.attendance.entity.student;

import com.studo.services.attendance.entity.CoEntity;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

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

    @Column(name = "AKADGRAD")
    public String title;

    @Column(name = "AKADGRAD_NACH")
    public String titleAfter;

    @Column(name = "GEBURTSDATUM")
    public Date birthdate;

    @Column(name = "GESCHLECHT")
    public String gender;

    @Column(name = "NATIONENBEZEICHNUNG")
    public String citizenship;

    @Column(name = "NATIONENBEZEICHNUNG2")
    public String secondCitizenship;

    @Column(name = "AUFNAHMEDATUM")
    public Date matriculationDate;

    @Column(name = "ABMELDUNGS_DATUM")
    public Date exmatriculationDate;
}
