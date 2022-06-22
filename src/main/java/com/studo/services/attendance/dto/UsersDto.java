package com.studo.services.attendance.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RegisterForReflection
public class UsersDto {
    public List<IdentityDto> identities;
    public List<StudentDto> students;
    public List<StaffDto> staffs;
}
