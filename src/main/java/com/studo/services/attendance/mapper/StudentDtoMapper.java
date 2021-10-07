package com.studo.services.attendance.mapper;

import com.studo.services.attendance.dto.SimplifiedCourseDto;
import com.studo.services.attendance.dto.StudentDto;
import com.studo.services.attendance.entity.c02.IDPrefix;
import com.studo.services.attendance.entity.course.CourseGroupEntity;
import com.studo.services.attendance.entity.student.StudentEntity;
import com.studo.services.attendance.service.CourseService;

import java.util.List;
import java.util.stream.Collectors;

import static com.studo.services.attendance.mapper.UserDtoMapper.getUserId;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
public class StudentDtoMapper {

    public static StudentDto mapStudentDto(StudentEntity studentEntity, IDPrefix idPrefix) {
        return new StudentDto(
                getUserId(studentEntity.id, idPrefix),
                studentEntity.matriculationNumber,
                getSimplifiedCourseDtos(studentEntity),
                getStudyPrograms(studentEntity)
        );
    }

    private static List<String> getStudyPrograms(StudentEntity studentEntity) {
        return studentEntity.courseStudentEntities.stream()
                .map(courseStudentEntity -> courseStudentEntity.studyEntity.name).distinct()
                .collect(Collectors.toList());
    }

    private static List<SimplifiedCourseDto> getSimplifiedCourseDtos(StudentEntity studentEntity) {
        return studentEntity.courseStudentEntities.stream()
                .filter(courseStudentEntity -> courseStudentEntity.courseGroupEntity.deleted.equals(
                    CourseService.NOT_DELETED_COURSE_GROUP))
                .map(courseStudentEntity ->
                        mapSimplifiedCourseDto(courseStudentEntity.courseGroupEntity))
                .distinct().collect(Collectors.toList());
    }

    public static SimplifiedCourseDto mapSimplifiedCourseDto(CourseGroupEntity courseGroupEntityInput) {
        return new SimplifiedCourseDto(
                courseGroupEntityInput.courseEntity.id,
                courseGroupEntityInput.id
        );
    }
}
