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
public class StudentDto {
    public BigDecimal userId;
    public String matriculationNumber;
    public List<SimplifiedCourseDto> courses;
    public List<String> studyPrograms;
}