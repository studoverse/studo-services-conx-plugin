package at.co.attendance.entity.c02;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Entity
@Immutable
@Table(name = "CO_LOC_RESOURCE_TYPE_FILTER")
public class ResourceTypeFilter extends PanacheEntity {
    @Column(name = "RESOURCE_TYPE")
    public String courseResourceType;
}
