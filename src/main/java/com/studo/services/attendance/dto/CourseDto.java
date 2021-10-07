package com.studo.services.attendance.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@AllArgsConstructor
@RegisterForReflection
public class CourseDto {
    public BigDecimal courseId;
    public BigDecimal organizationId;
    public String courseNumber;
    public String name;
    public String academicYear;
    public String semester;
    public List<CourseGroupDto> groups;
}
