package com.studo.services.attendance.service;

import com.studo.services.attendance.dto.CourseDto;
import com.studo.services.attendance.entity.course.CourseEntity;
import com.studo.services.attendance.mapper.CourseDtoMapper;
import com.studo.services.attendance.repository.CourseRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class CourseService {
    @Inject
    CourseRepository courseRepository;

    public List<CourseDto> getCourses(List<CourseEntity> courseEntities) {
        return courseEntities.stream()
                .map(courseEntity -> CourseDtoMapper.mapCourseDto(courseEntity)).collect(Collectors.toList());
    }

    public List<CourseEntity> getCourseEntities(String academicYear, List<String> semesters) {
        return courseRepository.getCourseEntities(academicYear, semesters);
    }

}
