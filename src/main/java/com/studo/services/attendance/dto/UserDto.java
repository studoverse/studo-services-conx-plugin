package com.studo.services.attendance.dto;

import com.studo.services.attendance.entity.function.FunctionEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@AllArgsConstructor
@RegisterForReflection
public class UserDto {
    public BigDecimal userId;
    public String obfuscatedId;
    public String firstName;
    public String lastName;
    public String email;
    public List<String> permissions;
    @JsonIgnore
    public List<FunctionEntity> staffFunctionEntities;
}
