package com.studo.services.attendance.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@AllArgsConstructor
@RegisterForReflection
public class AttendanceDto {
    public List<CourseDto> courses;
    public List<StudentDto> students;
    public List<StaffDto> staffs;
    public List<IdentityDto> identities;
}
