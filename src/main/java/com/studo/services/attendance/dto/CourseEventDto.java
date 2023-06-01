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
    public Integer teachingUnitsAgh;
    public Integer teachingUnitsCancelled;
    public String type;
    public String typeName;
    public String place;
    public String courseResourceType;
    public BigDecimal seriesNr;
    public BigDecimal modErlerneNr;
    public String title;
    public String comment;
    public String internalComment;
    public BigDecimal previousEventId;
    public Date createdOn;
    public BigDecimal lecturerStaffId; // TODO insert staffId of lecturer who will teach at this event.
}
