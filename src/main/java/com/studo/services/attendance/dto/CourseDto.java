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
    public String courseNumber;
    public String academicYear;
    public String semester;
    public String name;
    public String nameEn;
    public BigDecimal semesterHours;
    public String type;
    public String typeName;
    public String allStates;
    public BigDecimal organizationId;
    public BigDecimal examiningOrganisationId;
    public BigDecimal contactHours;
    public String credits;
    public BigDecimal weighting;
    public List<CourseGroupDto> groups;
}
