package com.studo.services.attendance.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@AllArgsConstructor
@RegisterForReflection
public class StudentDto {
    public BigDecimal id;
    public String lastName;
    public String firstName;
    public String matriculationNumber;
    public String email;
    public String title;
    public String titleAfter;
    public Date birthdate;
    public String gender;
    public String citizenship;
    public String secondCitizenship;
    public Date matriculationDate;
    public Date exmatriculationDate;
}