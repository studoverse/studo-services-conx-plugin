package com.studo.services.attendance.service;

import com.studo.services.attendance.dto.OrganisationDto;
import com.studo.services.attendance.entity.c02.OrganisationFilter;
import com.studo.services.attendance.entity.course.CourseEntity;
import com.studo.services.attendance.entity.function.FunctionEntity;
import com.studo.services.attendance.entity.organisation.OrganisationEntity;
import com.studo.services.attendance.mapper.OrganisationDtoMapper;
import com.studo.services.attendance.repository.CourseRepository;
import com.studo.services.attendance.repository.FunctionRepository;
import com.studo.services.attendance.repository.OrganisationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

import static com.studo.services.attendance.service.CourseService.NOT_DELETED_COURSE_GROUP;
import static java.util.Optional.ofNullable;


/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class OrganisationService {

    @Inject
    FunctionRepository functionRepository;

    @Inject
    CourseRepository courseRepository;

    @Inject
    OrganisationRepository organisationRepository;

    public List<OrganisationDto> getOrganisations() {
        List<OrganisationFilter> organisationTeamsFilterViews = organisationRepository.getOrganisationFilters();

        List<FunctionEntity> functionEntities = functionRepository.getFunctionEntities().stream()
                .filter(functionEntity -> ofNullable(functionEntity).map(functionEntity1 -> functionEntity1.staffEntity).isPresent()).collect(Collectors.toList());

        List<CourseEntity> courseEntities = filterCourseEntities(courseRepository.getCourseEntities(organisationTeamsFilterViews));

        List<OrganisationEntity> organisationEntities =
                getOrganisationEntities(organisationRepository.listAll(), courseEntities) ;

        return organisationEntities.stream()
                .map(organisationEntity ->
                        OrganisationDtoMapper.mapOrganisationDto(
                                organisationEntity, filterCoursesByOrg(courseEntities, organisationEntity),
                                functionEntities)).collect(Collectors.toList());
    }

    private List<CourseEntity> filterCoursesByOrg(List<CourseEntity> courseEntities, OrganisationEntity organisationEntity) {
        return courseEntities.stream()
                .filter(courseEntity -> courseEntity.organisationEntity.id.equals(organisationEntity.id)).collect(Collectors.toList());
    }

    private List<CourseEntity> filterCourseEntities(List<CourseEntity> courseEntities) {
        return courseEntities.stream().filter(courseEntity ->
                courseEntity.courseGroupEntities.stream()
                        .anyMatch(courseGroupEntity -> courseGroupEntity.deleted.equals(NOT_DELETED_COURSE_GROUP))).collect(Collectors.toList());
    }

    private List<OrganisationEntity> getOrganisationEntities(List<OrganisationEntity> organisationEntities,
                                                             List<CourseEntity> courseEntities) {
        return organisationEntities.stream().filter(organisationEntity ->
                        courseEntities.stream().anyMatch(courseEntity ->
                                courseEntity.organisationEntity.id.equals(organisationEntity.id))).distinct().collect(Collectors.toList());
    }

}
