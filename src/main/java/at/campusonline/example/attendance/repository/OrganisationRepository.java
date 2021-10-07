package at.co.attendance.repository;

import at.co.attendance.entity.c02.OrganisationFilter;
import at.co.attendance.entity.organisation.OrganisationEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class OrganisationRepository implements PanacheRepository<OrganisationEntity> {

    public List<OrganisationEntity> getOrganisationEntities(List<BigDecimal> orgIDs) {
        return OrganisationEntity.list("NR in ?1", orgIDs);
    }
    public List<OrganisationFilter> getOrganisationFilters() {
        return OrganisationFilter.listAll();
    }

}
