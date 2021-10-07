package at.co.attendance.entity.course;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_LV_GRUPPEN_V", schema = "TUG_NEW")
public class CourseGroupEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    @Column(name = "LV_GRP_NR")
    public BigDecimal id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STP_SP_NR", referencedColumnName = "STP_SP_NR")
    public CourseEntity courseEntity;

    @Column(name = "NAME")
    public String name;

    @Column(name = "GELOESCHT_FLAG")
    public String deleted;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "courseGroupEntity")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<CourseStaffEntity> courseStaffEntities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "courseGroupEntity")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<CourseStudentEntity> courseStudentEntities;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "courseGroupEntity")
    @Fetch(value = FetchMode.SUBSELECT)
    public List<CourseEventEntity> courseEventEntities;

}
