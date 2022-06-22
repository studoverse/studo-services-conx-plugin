package com.studo.services.attendance.repository;

import com.studo.services.attendance.entity.course.CourseEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class CourseRepository implements PanacheRepository<CourseEntity> {
    public List<CourseEntity> getCourseEntities(String academicYear,
                                                List<String> semesters
    ) {
        return CourseEntity.list("LV_STATUS_ALLE like ?1 and SJ_NAME in ?2 and semester in ?3",
                "%BF%",
                academicYear,
                semesters
        );
    }

}
