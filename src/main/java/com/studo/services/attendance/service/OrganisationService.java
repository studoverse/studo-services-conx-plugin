package com.studo.services.attendance.service;

import com.studo.services.attendance.dto.OrganisationDto;
import com.studo.services.attendance.entity.c02.OrganisationFilter;
import com.studo.services.attendance.entity.organisation.OrganisationEntity;
import com.studo.services.attendance.mapper.OrganisationDtoMapper;
import com.studo.services.attendance.repository.OrganisationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class OrganisationService {

    @Inject
    OrganisationRepository organisationRepository;

    public List<OrganisationDto> getOrganisations() {
        List<OrganisationFilter> organisationFilters = organisationRepository.getOrganisationFilters();
        List<BigDecimal> orgIds =
                organisationFilters.stream().map(organisationFilter -> organisationFilter.id).collect(Collectors.toList());

        List<OrganisationEntity> organisationEntities = organisationRepository.getOrganisationEntities(orgIds);
        return organisationEntities.stream().map(OrganisationDtoMapper::mapOrganisationDto).collect(Collectors.toList());
    }
}
