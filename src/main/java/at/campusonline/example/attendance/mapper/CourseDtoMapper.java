package at.co.attendance.mapper;

import at.co.attendance.dto.CourseDto;
import at.co.attendance.dto.CourseEventDto;
import at.co.attendance.dto.CourseGroupDto;
import at.co.attendance.dto.UserDto;
import at.co.attendance.entity.course.CourseEntity;
import at.co.attendance.entity.course.CourseEventEntity;
import at.co.attendance.entity.course.CourseGroupEntity;
import io.smallrye.common.constraint.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static at.co.attendance.service.CourseService.NOT_DELETED_COURSE_GROUP;
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
                        .filter(courseGroupEntity -> courseGroupEntity.deleted.equals(NOT_DELETED_COURSE_GROUP))
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
