
package com.studo.services.attendance.entity.organisation;

import com.studo.services.attendance.entity.CoEntity;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "PU_ORG_V", schema = "TUG_NEW")
public class OrganisationEntity extends CoEntity {

    @Column(name = "NAME")
    public String name;

    @OneToMany(mappedBy = "parent")
    public List<OrganisationEntity> children;

    @JoinColumn(name = "ORG_NR", referencedColumnName = "NR")
    @ManyToOne
    public OrganisationEntity parent;


    public List<OrganisationEntity> getAllParents(){
        return getAllParents(this, new ArrayList<>());
    }

    private List<OrganisationEntity> getAllParents(@NotNull OrganisationEntity parent, @NotNull List<OrganisationEntity> allParents){
        ofNullable(parent.parent).map(allParents::add);
        ofNullable(parent.parent).ifPresent(courseEntities -> getAllParents(courseEntities, allParents));
        return allParents;
    }
}
