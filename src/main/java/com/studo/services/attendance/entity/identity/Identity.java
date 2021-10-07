package com.studo.services.attendance.entity.identity;

import com.studo.services.attendance.entity.CoEntity;
import com.studo.services.attendance.entity.staff.StaffEntity;
import com.studo.services.attendance.entity.student.StudentEntity;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_IDENTITAETEN_V", schema = "TUG_NEW")
public class Identity extends CoEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "PERSON_NR", referencedColumnName = "NR", insertable=false, updatable=false)
    public StaffEntity staffEntity;

    @OneToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "ST_PERSON_NR", referencedColumnName = "NR", insertable=false, updatable=false)
    public StudentEntity studentEntity;

    @Column(name = "NR_OBFUSCATED")
    public String obfuscatedId;
}
