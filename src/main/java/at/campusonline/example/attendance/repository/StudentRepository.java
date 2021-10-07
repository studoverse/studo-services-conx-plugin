package at.co.attendance.repository;

import at.co.attendance.entity.student.StudentEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class StudentRepository implements PanacheRepository<StudentEntity> {
}
