package com.studo.services.attendance.repository;

import com.studo.services.attendance.entity.c02.*;
import com.studo.services.attendance.entity.course.CourseEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class CourseRepository implements PanacheRepository<CourseEntity> {
    public List<CourseEntity> getCourseEntities(List<OrganisationFilter> organisationTeamsFilterViews) {
        List<CourseFilter> courseFilters = CourseFilter.listAll();
        return CourseEntity.list("LV_STATUS_ALLE like ?1 and ORG_NR_BETREUT in ?2 and SJ_NAME in ?3 and semester in ?4 ",
                "%BF%",
                organisationTeamsFilterViews.stream().map(o -> o.id).collect(Collectors.toList()),
                courseFilters.stream().map(courseFilter -> courseFilter.academicYear).collect(Collectors.toList()),
                Stream.concat(courseFilters.stream().map(courseFilter -> courseFilter.semesterS),
                        courseFilters.stream().map(courseFilter -> courseFilter.semesterW)).collect(Collectors.toList())
        );
    }
    public List<String> getResourceFilter() {
        List<ResourceTypeFilter> resourceTypeFilters = ResourceTypeFilter.listAll();
        return resourceTypeFilters.stream().map(resourceTypeFilter -> resourceTypeFilter.courseResourceType).collect(Collectors.toList());
    }

    public List<String> getEventFilter() {
        List<EventTypeFilter> eventTypeFilters = EventTypeFilter.listAll();
        return eventTypeFilters.stream().map(eventTypeFilter -> eventTypeFilter.eventType).collect(Collectors.toList());
    }
}
