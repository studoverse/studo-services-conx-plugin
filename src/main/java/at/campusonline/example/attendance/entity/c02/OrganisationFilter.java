package at.co.attendance.entity.c02;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "CO_LOC_ATTENDANCE_ORG")
public class OrganisationFilter extends PanacheEntityBase {

    @Id
    @GeneratedValue
    @Column(name = "ORG_NR")
    public BigDecimal id;
}
