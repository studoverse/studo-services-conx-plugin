package com.studo.services.attendance.repository;

import com.studo.services.attendance.entity.course.CourseStudentEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class CourseStudentRepository implements PanacheRepository<CourseStudentEntity> {
}