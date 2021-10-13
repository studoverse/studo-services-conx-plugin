package com.studo.services.attendance.mapper;

import com.studo.services.attendance.dto.OrganisationDto;
import com.studo.services.attendance.entity.organisation.OrganisationEntity;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
public class OrganisationDtoMapper {
    public static OrganisationDto mapOrganisationDto(OrganisationEntity organisationEntity) {
        return new OrganisationDto(organisationEntity.id,
                organisationEntity.organizationName,
                organisationEntity.organisationId
        );
    }
}
