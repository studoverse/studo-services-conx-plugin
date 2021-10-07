package at.co.attendance.service;

import at.co.attendance.dto.CourseDto;
import at.co.attendance.dto.UserDto;
import at.co.attendance.entity.c02.OrganisationFilter;
import at.co.attendance.entity.course.CourseEntity;
import at.co.attendance.mapper.CourseDtoMapper;
import at.co.attendance.repository.CourseRepository;
import at.co.attendance.repository.FunctionRepository;
import at.co.attendance.repository.OrganisationRepository;

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
    FunctionRepository functionRepository;

    @Inject
    CourseRepository courseRepository;

    @Inject
    OrganisationRepository organisationRepository;

    public List<CourseDto> getCourses() {
        List<OrganisationFilter> organisationFilters = organisationRepository.getOrganisationFilters();
        List<CourseEntity> courseEntities = courseRepository.getCourseEntities(organisationFilters);

        courseEntities = courseEntities.stream().filter(courseEntity ->
                courseEntity.courseGroupEntities.stream()
                        .anyMatch(courseGroupEntity -> courseGroupEntity.deleted.equals(NOT_DELETED_COURSE_GROUP))).collect(Collectors.toList());

        List<CourseDto> courseDtoList = courseEntities.stream()
                .map(courseEntity -> CourseDtoMapper.mapCourseDto(courseEntity,
                        courseRepository.getResourceFilter(),
                        courseRepository.getEventFilter()
                        )).collect(Collectors.toList());

        courseDtoList.forEach(o ->
                o.groups.forEach(i->
                        i.internLecturers.forEach(e->
                                e.permissions = filterFunctions(organisationFilters, e))));
        return courseDtoList;
    }


    private List<String> filterFunctions(List<OrganisationFilter> organisationTeamsFilterViews,
                                         UserDto userDto) {
        return userDto.staffFunctionEntities.stream()
                .filter(f-> organisationTeamsFilterViews.stream().anyMatch(k-> k.id.equals(f.organisationEntity.id)))
                .map(n->n.type)
                .filter(functionRepository.getFunctionFilters()::contains).collect(Collectors.toList());
    }
}
