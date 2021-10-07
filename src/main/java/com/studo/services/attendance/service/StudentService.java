package com.studo.services.attendance.service;

import com.studo.services.attendance.dto.StudentDto;
import com.studo.services.attendance.entity.c02.IDPrefix;
import com.studo.services.attendance.entity.c02.OrganisationFilter;
import com.studo.services.attendance.entity.course.CourseStudentEntity;
import com.studo.services.attendance.entity.student.StudentEntity;
import com.studo.services.attendance.mapper.StudentDtoMapper;
import com.studo.services.attendance.repository.CourseRepository;
import com.studo.services.attendance.repository.CourseStudentRepository;
import com.studo.services.attendance.repository.OrganisationRepository;
import com.studo.services.attendance.repository.StudentRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class StudentService {

    @Inject
    OrganisationRepository organisationRepository;

    @Inject
    CourseStudentRepository courseStudentRepository;

    @Inject
    StudentRepository studentRepository;

    @Inject
    CourseRepository courseRepository;

    public List<StudentDto> getStudents() {
        List<OrganisationFilter> organisationTeamsFilterViews = organisationRepository.getOrganisationFilters();
        List<CourseStudentEntity> courseStudentEntities = getCourseStudentEntities(organisationTeamsFilterViews);

        var studentEntities = getStudentEntities(courseStudentEntities, studentRepository.listAll());
        IDPrefix idPrefix = (IDPrefix) IDPrefix.listAll().stream().findFirst().orElse(new IDPrefix());
        return studentEntities.stream()
                .map(studentEntity -> StudentDtoMapper.mapStudentDto(studentEntity, idPrefix)).collect(Collectors.toList());
    }

    private List<CourseStudentEntity> getCourseStudentEntities(List<OrganisationFilter> organisationTeamsFilterViews) {
        List<CourseStudentEntity> courseStudentEntities = courseStudentRepository.listAll();

        return courseStudentEntities.stream()
                .filter(courseStudentEntity ->
                        filterCourseStudentEntities(organisationTeamsFilterViews, courseStudentEntity)).collect(Collectors.toList());
    }

    private boolean filterCourseStudentEntities(List<OrganisationFilter> organisationTeamsFilterViews,
                                                CourseStudentEntity courseStudentEntity) {
        var s = courseRepository.getCourseStateFilter().stream().findFirst().orElse("");
        return courseStudentEntity.courseGroupEntity.deleted.equals(CourseService.NOT_DELETED_COURSE_GROUP) &&
                courseStudentEntity.courseEntity.allStates.contains(s) &&
                organisationTeamsFilterViews.stream()
                        .anyMatch(o -> o.id.equals(courseStudentEntity.courseEntity.organisationEntity.id));
    }

    private List<StudentEntity> getStudentEntities(List<CourseStudentEntity> courseStudentEntities,
                                                   List<StudentEntity> studentEntities) {
        return studentEntities.stream().filter(studentEntity ->
                courseStudentEntities.stream().anyMatch(courseStudentEntity ->
                        courseStudentEntity.studentEntity.id.equals(studentEntity.id))).collect(Collectors.toList());
    }

}