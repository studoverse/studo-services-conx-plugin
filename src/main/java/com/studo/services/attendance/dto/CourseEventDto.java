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
public class CourseEventDto {
    public BigDecimal eventId;
    public Date start;
    public Date end;
    public Integer teachingUnits;
    public String place;
}
