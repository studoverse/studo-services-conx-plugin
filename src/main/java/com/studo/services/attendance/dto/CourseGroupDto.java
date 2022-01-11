package com.studo.services.attendance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@AllArgsConstructor
@RegisterForReflection
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseGroupDto {
    public BigDecimal courseId;
    public BigDecimal groupId;
    public String name;
    public String deleted;
    public String isStandard;
    public Date registrationStart;
    public Date registrationEnd;
    public List<BigDecimal> lecturerStaffIds;
    public List<CourseEventDto> events;
    public List<BigDecimal> studentIds;
}
