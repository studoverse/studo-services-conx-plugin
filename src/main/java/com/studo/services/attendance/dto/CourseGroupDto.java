package com.studo.services.attendance.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
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
    public List<BigDecimal> lecturers;
    @JsonIgnore
    public List<UserDto> internLecturers;
    public List<CourseEventDto> events;
    @JsonIgnore
    public List<UserDto> students;
}
