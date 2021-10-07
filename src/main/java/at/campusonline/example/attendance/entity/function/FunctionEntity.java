package at.co.attendance.entity.function;

import at.co.attendance.entity.organisation.OrganisationEntity;
import at.co.attendance.entity.staff.StaffEntity;
import at.co.attendance.entity.staff.StaffFunctionCompositeKey;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_PERS_ORG_FUNK_DE_FUNKTYP_V", schema = "TUG_NEW")
public class FunctionEntity extends PanacheEntityBase {

    @EmbeddedId
    public StaffFunctionCompositeKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(name = "PERSON_NR", referencedColumnName = "NR", insertable=false, updatable=false)
    public StaffEntity staffEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORG_NR", referencedColumnName = "NR", insertable=false, updatable=false)
    public OrganisationEntity organisationEntity;

    @Column(name = "NAME")
    public String name;

    @Column(name = "KURZBEZEICHNUNG")
    public String type;
}
