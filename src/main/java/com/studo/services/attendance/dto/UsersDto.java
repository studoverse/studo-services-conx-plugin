package com.studo.services.attendance.dto;

import com.studo.services.attendance.entity.function.FunctionEntity;
import com.studo.services.attendance.entity.identity.IdentityEntity;
import com.studo.services.attendance.entity.staff.StaffEntity;
import com.studo.services.attendance.entity.student.StudentEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@RegisterForReflection
public class UsersDto {
    public List<IdentityEntity> identities;
    public List<StudentEntity> students;
    public List<StaffEntity> staffs;
}
