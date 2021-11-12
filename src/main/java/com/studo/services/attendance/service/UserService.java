package com.studo.services.attendance.service;

import com.studo.services.attendance.dto.IdentityDto;
import com.studo.services.attendance.dto.StaffDto;
import com.studo.services.attendance.dto.StudentDto;
import com.studo.services.attendance.entity.course.CourseEntity;
import com.studo.services.attendance.entity.function.FunctionEntity;
import com.studo.services.attendance.entity.identity.IdentityEntity;
import com.studo.services.attendance.entity.staff.StaffEntity;
import com.studo.services.attendance.entity.student.StudentEntity;
import com.studo.services.attendance.repository.FunctionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class UserService {

    @Inject
    EntityManager entityManager;

    @Inject
    FunctionRepository functionRepository;

    public List<IdentityDto> getIdentities(List<CourseEntity> courseEntities) {
        List<BigDecimal> studentIds = getStudentIds(courseEntities);
        if (studentIds.size() == 0) return new ArrayList<>();

        List<BigDecimal> staffIds = getStaffIds(courseEntities);
        if (staffIds.size() == 0) return new ArrayList<>();

        List<IdentityEntity> result = new ArrayList<>();

        var builder = entityManager.getCriteriaBuilder();
        var criteria = builder.createQuery(IdentityEntity.class);
        var courseEntityRoot = criteria.from(IdentityEntity.class);
        var predicate = createInStatement(builder, courseEntityRoot.get("studentId"), studentIds);
        criteria.select(courseEntityRoot).where(predicate);

        result.addAll(entityManager.createQuery(criteria).getResultList());

        criteria = builder.createQuery(IdentityEntity.class);
        courseEntityRoot = criteria.from(IdentityEntity.class);
        predicate = createInStatement(builder, courseEntityRoot.get("staffId"), staffIds);
        criteria.select(courseEntityRoot).where(predicate);

        result.addAll(entityManager.createQuery(criteria).getResultList());

        return result.stream().map(identityEntity ->
                        new IdentityDto(identityEntity.id,
                                identityEntity.staffId,
                                identityEntity.studentId,
                                identityEntity.obfuscatedId)).collect(Collectors.toList());
    }

    public List<StudentDto> getStudents(List<CourseEntity> courseEntities) {
        List<BigDecimal> studentIds = getStudentIds(courseEntities);
        if (studentIds.size() == 0) return new ArrayList<>();

        var builder = entityManager.getCriteriaBuilder();
        var criteria = builder.createQuery(StudentEntity.class);
        var courseEntityRoot = criteria.from(StudentEntity.class);
        var predicate = createInStatement(builder, courseEntityRoot.get("id"), studentIds);
        criteria.select(courseEntityRoot).where(predicate);
        var courseFilteredEntities = entityManager.createQuery(criteria).getResultList();

        return courseFilteredEntities.stream().map(studentEntity ->
                        new StudentDto(studentEntity.id,
                                studentEntity.lastName,
                                studentEntity.firstName,
                                studentEntity.matriculationNumber,
                                studentEntity.email)
                )
                .collect(Collectors.toList());
    }

    private List<BigDecimal> getStudentIds(List<CourseEntity> courseEntities) {
        List<BigDecimal> studentIds = courseEntities.stream()
                .map(courseEntity -> courseEntity.courseGroupEntities)
                .flatMap(courseGroupEntities -> courseGroupEntities.stream()
                        .map(courseGroupEntity -> courseGroupEntity.courseStudentEntities)
                        .flatMap(courseStudentEntities -> courseStudentEntities.stream()
                                .map(courseStudentEntity -> courseStudentEntity.studentId))
                ).distinct().collect(Collectors.toList());
        return studentIds;
    }

    public List<StaffDto> getStaff(List<CourseEntity> courseEntities) {
        List<BigDecimal> staffIds = getStaffIds(courseEntities);
        if (staffIds.size() == 0) return new ArrayList<>();

        var builder = entityManager.getCriteriaBuilder();
        var criteria = builder.createQuery(StaffEntity.class);
        var courseEntityRoot = criteria.from(StaffEntity.class);
        var predicate = createInStatement(builder, courseEntityRoot.get("id"), staffIds);
        criteria.select(courseEntityRoot).where(predicate);
        var staffEntities = entityManager.createQuery(criteria).getResultList();

        return staffEntities.stream().map(staffEntity ->
                        new StaffDto(staffEntity.id,
                                staffEntity.lastName,
                                staffEntity.firstName,
                                staffEntity.title,
                                staffEntity.email)
                )
                .collect(Collectors.toList());
    }

    private List<BigDecimal> getStaffIds(List<CourseEntity> courseEntities) {
        List<BigDecimal> staffIds = courseEntities.stream()
                .map(courseEntity -> courseEntity.courseGroupEntities)
                .flatMap(courseGroupEntities -> courseGroupEntities.stream()
                        .map(courseGroupEntity -> courseGroupEntity.courseStaffEntities)
                        .flatMap(courseStudentEntities -> courseStudentEntities.stream()
                                .map(courseStudentEntity -> courseStudentEntity.staffId))
                ).distinct().collect(Collectors.toList());
        return staffIds;
    }

    public List<FunctionEntity> getFunctions(List<String> functionTypes) {
        return functionRepository.getFunctionEntities(functionTypes);
    }

    public List<FunctionEntity> getAllFunctionsSlow() {
        return functionRepository.getAllFunctionEntitiesSlow();
    }


    public static Predicate createInStatement(CriteriaBuilder cb, Path fieldName, List values) {
        var parameterLimit = 999;
        int listSize = values.size();
        Predicate predicate = null;
        for (int i = 0; i < listSize; i += parameterLimit) {
            List subList = listSize > i + parameterLimit ? values.subList(i, (i + parameterLimit)) : values.subList(i, listSize);
            predicate = predicate == null ? fieldName.in(subList) : cb.or(predicate, fieldName.in(subList));
        }
        return predicate;
    }
}
