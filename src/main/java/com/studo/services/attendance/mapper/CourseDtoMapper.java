package com.studo.services.attendance.mapper;

import com.studo.services.attendance.dto.CourseDto;
import com.studo.services.attendance.dto.CourseEventDto;
import com.studo.services.attendance.dto.CourseGroupDto;
import com.studo.services.attendance.entity.course.CourseEntity;
import com.studo.services.attendance.entity.course.CourseEventEntity;
import com.studo.services.attendance.entity.course.CourseGroupEntity;
import io.smallrye.common.constraint.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
public class CourseDtoMapper {

    public static CourseDto mapCourseDto(@NotNull CourseEntity courseEntity) {
        return new CourseDto(
                courseEntity.id,
                courseEntity.courseCode,
                courseEntity.academicYear,
                courseEntity.semester,
                courseEntity.title,
                courseEntity.titleEn,
                courseEntity.semesterHours,
                courseEntity.type,
                courseEntity.typeName,
                courseEntity.allStates,
                courseEntity.organisationId,
                courseEntity.examiningOrganisationId,
                courseEntity.contactHours,
                courseEntity.credits,
                courseEntity.weighting,
                courseEntity.courseGroupEntities.stream()
                        .map(courseGroupEntity -> getCourseGroupDto(courseEntity, courseGroupEntity))
                        .collect(Collectors.toList())
        );
    }

    private static CourseGroupDto getCourseGroupDto(CourseEntity courseEntity,
                                                    CourseGroupEntity courseGroupEntity) {
        return new CourseGroupDto(
                courseEntity.id,
                courseGroupEntity.id,
                courseGroupEntity.name,
                courseGroupEntity.deleted,
                courseGroupEntity.isStandard,
                courseGroupEntity.registrationStart,
                courseGroupEntity.registrationEnd,
                getStaffIds(courseGroupEntity),
                getCourseEventDtos(courseGroupEntity),
                getStudentIds(courseGroupEntity)
        );
    }

    private static CourseEventDto getCourseEventDto(CourseEventEntity courseEventEntity) {
        return new CourseEventDto(
                courseEventEntity.id,
                courseEventEntity.start,
                courseEventEntity.end,
                courseEventEntity.teachingUnits,
                courseEventEntity.teachingUnitsAgh,
                courseEventEntity.teachingUnitsCancelled,
                courseEventEntity.type,
                courseEventEntity.typeName,
                courseEventEntity.place,
                courseEventEntity.courseResourceType,
                courseEventEntity.seriesNr,
                courseEventEntity.modErlerneNr,
                courseEventEntity.title,
                courseEventEntity.comment,
                courseEventEntity.internalComment,
                courseEventEntity.previousEventId,
                courseEventEntity.createdOn
        );
    }

    private static List<BigDecimal> getStudentIds(CourseGroupEntity courseGroupEntity) {
        return courseGroupEntity.courseStudentEntities.stream()
                .map(courseStaffEntity -> courseStaffEntity.studentId).collect(Collectors.toList());
    }

    private static List<CourseEventDto> getCourseEventDtos(CourseGroupEntity courseGroupEntity) {
        return courseGroupEntity.courseEventEntities.stream()
                .map(CourseDtoMapper::getCourseEventDto).collect(Collectors.toList());
    }

    private static List<BigDecimal> getStaffIds(CourseGroupEntity courseGroupEntity) {
        return courseGroupEntity.courseStaffEntities.stream()
                .map(courseStaffEntity -> courseStaffEntity.staffId).collect(Collectors.toList());
    }
}
