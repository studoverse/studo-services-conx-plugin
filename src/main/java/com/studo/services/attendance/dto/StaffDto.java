package com.studo.services.attendance.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@AllArgsConstructor
@RegisterForReflection
public class StaffDto {
    public BigDecimal id;
    public String lastName;
    public String firstName;
    public String title;
    public String email;
}