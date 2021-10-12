package com.studo.services.attendance.entity.course;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@Embeddable
public class CourseStaffComposedKey implements Serializable {

    @Column(name = "LV_GRP_NR", nullable = true)
    private String courseGroupId;

    @Column(name = "STP_SP_NR", nullable = true)
    private String courseId;

    @Column(name = "PERSON_NR", nullable = true)
    private String staffId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseStaffComposedKey that = (CourseStaffComposedKey) o;
        return Objects.equals(courseGroupId, that.courseGroupId) &&
                Objects.equals(courseId, that.courseId) && Objects.equals(staffId, that.staffId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseGroupId, courseId, staffId);
    }
}
