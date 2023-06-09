package com.studo.services.attendance.repository;

import com.studo.services.attendance.entity.organisation.OrganisationEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class OrganisationRepository implements PanacheRepository<OrganisationEntity> {

    public List<OrganisationEntity> getOrganisationEntities() {
        return OrganisationEntity.listAll();
    }

}
