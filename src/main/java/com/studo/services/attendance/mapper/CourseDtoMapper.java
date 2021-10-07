package com.studo.services.attendance.mapper;

import com.studo.services.attendance.dto.CourseDto;
import com.studo.services.attendance.dto.CourseEventDto;
import com.studo.services.attendance.dto.CourseGroupDto;
import com.studo.services.attendance.dto.UserDto;
import com.studo.services.attendance.entity.course.CourseEntity;
import com.studo.services.attendance.entity.course.CourseEventEntity;
import com.studo.services.attendance.entity.course.CourseGroupEntity;
import com.studo.services.attendance.service.CourseService;
import io.smallrye.common.constraint.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
public class CourseDtoMapper {

    public static CourseDto mapCourseDto(@NotNull CourseEntity courseEntity, List<String> eventFilter, List<String> resourceFilter) {
        return new CourseDto(
                courseEntity.id,
                courseEntity.organisationEntity.id,
                courseEntity.courseCode,
                courseEntity.title,
                courseEntity.academicYear,
                courseEntity.semester,
                courseEntity.courseGroupEntities.stream()
                        .filter(courseGroupEntity -> courseGroupEntity.deleted.equals(CourseService.NOT_DELETED_COURSE_GROUP))
                        .map(courseGroupEntity -> getCourseGroupDto(courseEntity, courseGroupEntity, resourceFilter, eventFilter)).collect(Collectors.toList())
        );
    }

    private static CourseGroupDto getCourseGroupDto(CourseEntity courseEntity,
                                                    CourseGroupEntity courseGroupEntity,
                                                    List<String> eventFilter,
                                                    List<String> resourceFilter) {
        return new CourseGroupDto(
                courseEntity.id,
                courseGroupEntity.id,
                courseGroupEntity.name,
                getStaffIds(courseGroupEntity),
                getStaffDtos(courseGroupEntity),
                getCourseEventDtos(courseGroupEntity, resourceFilter, eventFilter),
                getStudents(courseGroupEntity)
        );
    }

    private static CourseEventDto getCourseEventDto(CourseEventEntity courseEventEntity) {
        return new CourseEventDto(
                courseEventEntity.id,
                courseEventEntity.start,
                courseEventEntity.end,
                courseEventEntity.teachingUnits,
                courseEventEntity.place
        );
    }

    private static Stream<CourseEventEntity> getCourseEventFiltered(CourseGroupEntity courseGroupEntity,
                                                                    List<String> eventFilter,
                                                                    List<String> resourceFilter) {
        return courseGroupEntity.courseEventEntities.stream()
                .filter(courseEventEntity -> eventFilter.stream().anyMatch(s -> s.equals(courseEventEntity.type)))
                .filter(courseEventEntity -> resourceFilter.stream().anyMatch(s -> s.equals(courseEventEntity.courseResourceType)));
    }
    private static List<UserDto> getStudents(CourseGroupEntity courseGroupEntity) {
        return courseGroupEntity.courseStudentEntities.stream()
                .filter(courseStudentEntity -> ofNullable(courseStudentEntity).isPresent() &&
                        ofNullable(courseStudentEntity.studentEntity).isPresent())
                .map(courseStudentEntity -> courseStudentEntity.studentEntity)
                .map(UserDtoMapper::getUserDto).collect(Collectors.toList());
    }

    private static List<CourseEventDto> getCourseEventDtos(CourseGroupEntity courseGroupEntity,
                                                           List<String> eventFilter,
                                                           List<String> resourceFilter) {
        return getCourseEventFiltered(courseGroupEntity, resourceFilter, eventFilter)
                .map(CourseDtoMapper::getCourseEventDto).collect(Collectors.toList());
    }

    private static List<UserDto> getStaffDtos(CourseGroupEntity courseGroupEntity) {
        return courseGroupEntity.courseStaffEntities.stream()
                .map(courseStaffEntity -> courseStaffEntity.staffEntity)
                .map(UserDtoMapper::getUserDto).collect(Collectors.toList());
    }

    private static List<BigDecimal> getStaffIds(CourseGroupEntity courseGroupEntity) {
        return courseGroupEntity.courseStaffEntities.stream()
                .map(courseStaffEntity -> courseStaffEntity.staffEntity.id).collect(Collectors.toList());
    }
}
