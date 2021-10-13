package com.studo.services.attendance.entity.staff;

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
@Table(name = "PU_PERSONEN_V", schema = "TUG_NEW")
public class StaffEntity extends CoEntity {

    @Column(name = "FAMILIENNAME")
    public String lastName;

    @Column(name = "VORNAME")
    public String firstName;

    @Column(name = "TITEL")
    public String title;

    @Column(name = "AKGRAD")
    public String academicTitle;

    @Column(name = "AMTSTITEL")
    public String officialTitle;

    @Column(name = "SONSTTITEL")
    public String otherTitle;

    @Column(name = "EMAIL_ADRESSE")
    public String email;

    @Column(name = "GESCHLECHT")
    public String gender;

    @Column(name = "GEBURTSDATUM")
    public String birthdate;

    @Column(name = "TELEFON_NUMMER")
    public String phoneNumber;
}
