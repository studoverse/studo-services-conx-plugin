package at.co.attendance.entity.course;

import at.co.attendance.entity.staff.StaffEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_LV_GRP_LEHRENDE_V", schema = "TUG_NEW")
public class CourseStaffEntity extends PanacheEntityBase {

    @EmbeddedId
    public CourseStaffComposedKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LV_GRP_NR", referencedColumnName = "LV_GRP_NR", insertable=false, updatable=false)
    public CourseGroupEntity courseGroupEntity;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERSON_NR", referencedColumnName = "NR", insertable=false, updatable=false)
    public StaffEntity staffEntity;

    @Column(name = "LA_P_FUNK_NAME")
    public String name;
}
