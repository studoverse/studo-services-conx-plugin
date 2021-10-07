package at.co.attendance.entity.student;

import at.co.attendance.entity.CoEntity;
import at.co.attendance.entity.course.CourseStudentEntity;
import at.co.attendance.entity.identity.Identity;
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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "studentEntity")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<CourseStudentEntity> courseStudentEntities;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "studentEntity")
    public Identity identity;

}
