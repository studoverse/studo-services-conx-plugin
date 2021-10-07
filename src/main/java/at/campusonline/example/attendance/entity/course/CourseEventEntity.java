package at.co.attendance.entity.course;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_LV_TE_RESOURCEN_V", schema = "TUG_NEW")
public class CourseEventEntity extends PanacheEntityBase {
    @Id
    @GeneratedValue
    @Column(name = "TERMIN_NR")
    public BigDecimal id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STP_SP_NR", referencedColumnName = "STP_SP_NR")
    public CourseEntity courseEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LV_GRP_NR", referencedColumnName = "LV_GRP_NR", insertable=false, updatable=false)
    public CourseGroupEntity courseGroupEntity;

    @Column(name = "TERMIN_ZEIT_VON")
    public Date start;

    @Column(name = "TERMIN_ZEIT_BIS")
    public Date end;

    @Column(name = "UNTERRICHTSEINHEITEN")
    public Integer teachingUnits;

    @Column(name = "TERMIN_EREIGNIS_TYP_KB")
    public String type;

    @Column(name = "ORT")
    public String place;

    @Column(name = "TERMIN_TYP_KB")
    public String courseResourceType;
}
