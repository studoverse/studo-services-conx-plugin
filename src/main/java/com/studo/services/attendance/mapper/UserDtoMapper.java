package com.studo.services.attendance.mapper;

import com.studo.services.attendance.dto.CourseDto;
import com.studo.services.attendance.dto.UserDto;
import com.studo.services.attendance.entity.c02.IDPrefix;
import com.studo.services.attendance.entity.function.FunctionEntity;
import com.studo.services.attendance.entity.organisation.OrganisationEntity;
import com.studo.services.attendance.entity.staff.StaffEntity;
import com.studo.services.attendance.entity.student.StudentEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
public class UserDtoMapper {

    public static UserDto getUserDto(StaffEntity functionEntity) {
        return new UserDto(functionEntity.id,
                functionEntity.identity.obfuscatedId,
                functionEntity.firstName,
                functionEntity.lastName,
                functionEntity.email,
                getPermissions(functionEntity.staffFunctionEntities),
                functionEntity.staffFunctionEntities);
    }
    public static UserDto getUserDto(StudentEntity courseStudentEntity) {
        List<String> studentFunctions = getStudentFunctions(courseStudentEntity);
        return new UserDto(courseStudentEntity.id,
                courseStudentEntity.identity.obfuscatedId,
                courseStudentEntity.firstName,
                courseStudentEntity.lastName,
                courseStudentEntity.email,
                studentFunctions,
                getStaffFunctionEntities(courseStudentEntity));
    }

    public static List<UserDto> getUserDtos(List<OrganisationEntity> allParents, List<FunctionEntity> functionEntities) {
        if (allParents.size() == 0) return new ArrayList<>();
        var filteredFunctionEntities = functionEntities.stream()
                .filter(functionEntity ->
                        allParents.stream()
                                .anyMatch(organisationEntity ->
                                        organisationEntity.id.equals(functionEntity.organisationEntity.id))).collect(Collectors.toList());

        return filteredFunctionEntities.stream().map(functionEntity -> functionEntity.staffEntity).map(UserDtoMapper::getUserDto)
                .distinct().collect(Collectors.toList());
    }

    private static List<FunctionEntity> getStaffFunctionEntities(StudentEntity courseStudentEntity) {
        return ofNullable(courseStudentEntity)
                .map(studentEntity -> studentEntity.identity)
                .map(identity -> identity.staffEntity)
                .map(staffEntity -> staffEntity.staffFunctionEntities)
                .map(functionEntities -> courseStudentEntity.identity.staffEntity.staffFunctionEntities)
                .orElse(null);
    }

    private static List<String> getStudentFunctions(StudentEntity courseStudentEntity) {
        return ofNullable(courseStudentEntity)
                .map(studentEntity -> studentEntity.identity)
                .map(identity -> identity.staffEntity)
                .map(staffEntity ->
                        getStaffFunctionEntities(courseStudentEntity)
                                .stream()
                                .filter(functionEntity -> ofNullable(functionEntity).map(functionEntity1 -> functionEntity1.type).isPresent())
                                .map(functionEntity -> functionEntity.type).collect(Collectors.toList()))
                .orElse(new ArrayList<>());
    }

    public static List<UserDto> updateStudentId(List<CourseDto> courses,
                                                List<UserDto> collectTeachers,
                                                IDPrefix idPrefix) {
        List<UserDto> collectStudents = courses.stream()
                .flatMap(courseDto -> courseDto.groups.stream().flatMap(courseGroupDto -> courseGroupDto.students.stream()))
                .distinct()
                .peek(userDto -> userDto.userId = getUserId(userDto.userId, idPrefix)).collect(Collectors.toList());

        return Stream.concat(collectTeachers.stream(), collectStudents.stream())
                .filter(distinctByKey(userDto ->  userDto.userId)).distinct().collect(Collectors.toList());
    }

    static BigDecimal getUserId(BigDecimal userId, IDPrefix idPrefix) {
        return new BigDecimal(idPrefix.prefix + userId.toString());
    }

    private static List<String> getPermissions(List<FunctionEntity> staffFunctionEntities) {
        return staffFunctionEntities.stream().map(staffFunctionEntity -> staffFunctionEntity.type).distinct().collect(Collectors.toList());
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
