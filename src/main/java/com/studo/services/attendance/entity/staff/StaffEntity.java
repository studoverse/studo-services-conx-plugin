package com.studo.services.attendance.entity.staff;

import com.studo.services.attendance.entity.CoEntity;
import com.studo.services.attendance.entity.function.FunctionEntity;
import com.studo.services.attendance.entity.identity.Identity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "EMAIL_ADRESSE")
    public String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "staffEntity")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<FunctionEntity> staffFunctionEntities;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "staffEntity")
    public Identity identity;

}
