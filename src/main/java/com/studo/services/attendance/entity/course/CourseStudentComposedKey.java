package com.studo.services.attendance.entity.course;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Embeddable
public class CourseStudentComposedKey implements Serializable {

    @Column(name = "LV_GRP_NR")
    private String courseGroupId;

    @Column(name = "LV_GRP_PERSONEN_NR")
    private String courseGroupPersonId;

    @Column(name = "STP_SP_NR")
    private String courseId;


    @Column(name = "SJ_NR")
    private String academicYearId;

    @Column(name = "ST_PERSON_NR")
    private String staffId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseStudentComposedKey that = (CourseStudentComposedKey) o;
        return Objects.equals(courseGroupId, that.courseGroupId) && Objects.equals(courseId, that.courseId) && Objects.equals(staffId, that.staffId) && Objects.equals(academicYearId, that.academicYearId) && Objects.equals(courseGroupPersonId, that.courseGroupPersonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseGroupId, courseId, staffId , academicYearId, courseGroupPersonId);
    }
}
