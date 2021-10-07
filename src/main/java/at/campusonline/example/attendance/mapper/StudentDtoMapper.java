package at.co.attendance.mapper;

import at.co.attendance.dto.SimplifiedCourseDto;
import at.co.attendance.dto.StudentDto;
import at.co.attendance.entity.c02.IDPrefix;
import at.co.attendance.entity.course.CourseGroupEntity;
import at.co.attendance.entity.student.StudentEntity;

import java.util.List;
import java.util.stream.Collectors;

import static at.co.attendance.mapper.UserDtoMapper.getUserId;
import static at.co.attendance.service.CourseService.NOT_DELETED_COURSE_GROUP;

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
                .filter(courseStudentEntity -> courseStudentEntity.courseGroupEntity.deleted.equals(NOT_DELETED_COURSE_GROUP))
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
