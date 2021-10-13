package com.studo.services.attendance.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@AllArgsConstructor
@RegisterForReflection
public class IdentityDto {
    public BigDecimal id;
    public BigDecimal staffId;
    public BigDecimal studentId;
    public String obfuscatedId;
}
