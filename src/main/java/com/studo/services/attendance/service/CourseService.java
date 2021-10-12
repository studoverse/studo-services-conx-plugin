package com.studo.services.attendance.service;

import com.studo.services.attendance.dto.CourseDto;
import com.studo.services.attendance.entity.c02.OrganisationFilter;
import com.studo.services.attendance.entity.course.CourseEntity;
import com.studo.services.attendance.mapper.CourseDtoMapper;
import com.studo.services.attendance.repository.CourseRepository;
import com.studo.services.attendance.repository.OrganisationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class CourseService {

    public final static String NOT_DELETED_COURSE_GROUP = "N";

    @Inject
    CourseRepository courseRepository;

    @Inject
    OrganisationRepository organisationRepository;

    public List<CourseDto> getCourses(List<CourseEntity> courseEntities) {
        List<String> resourceFilter = courseRepository.getResourceFilter();
        List<String> eventFilter = courseRepository.getEventFilter();
        return courseEntities.stream()
                .map(courseEntity ->
                        CourseDtoMapper.mapCourseDto(courseEntity, resourceFilter, eventFilter)).collect(Collectors.toList());
    }

    public List<CourseEntity> getCourseEntities() {
        List<OrganisationFilter> organisationFilters = organisationRepository.getOrganisationFilters();
        return courseRepository.getCourseEntities(organisationFilters);
    }
}
