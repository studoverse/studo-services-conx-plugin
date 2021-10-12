package com.studo.services.attendance.rest;

import at.campusonline.pub.auth.api.jaxrs.UserSessionDisabled;
import com.studo.services.attendance.dto.AttendanceDto;
import com.studo.services.attendance.dto.IdentityDto;
import com.studo.services.attendance.dto.OrganisationDto;
import com.studo.services.attendance.entity.course.CourseEntity;
import com.studo.services.attendance.entity.function.FunctionEntity;
import com.studo.services.attendance.entity.study.StudyEntity;
import com.studo.services.attendance.service.OrganisationService;
import com.studo.services.attendance.service.CourseService;
import com.studo.services.attendance.service.UserService;
import org.jboss.resteasy.annotations.GZIP;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 *
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@GZIP
@ApplicationScoped
// disable the user session to allow machine to machine communication
@UserSessionDisabled
@Produces("application/json")
@Path("rest/attendance")
public class AttendanceRestService {

    @Inject
    OrganisationService organisationService;

    @Inject
    CourseService courseService;

    @Inject
    UserService userService;

    @GET
    public AttendanceDto getAttendance() {
        List<CourseEntity> courseEntities = courseService.getCourseEntities();
        return new AttendanceDto(
                courseService.getCourses(courseEntities),
                userService.getStudents(courseEntities),
                userService.getStaff(courseEntities)
        );
    }

    @GET
    @Path("functions")
    public List<FunctionEntity> getFunctions() {
        return userService.getFunctions();
    }

    @GET
    @Path("organisations")
    public List<OrganisationDto> getOrganisations() {
        return organisationService.getOrganisations();
    }

    // slow CO Public views
    @GET
    @Path("identities")
    public List<IdentityDto> getIdentities() {
        return userService.getIdentities();
    }

    // slow CO Public views
    @GET
    @Path("studies")
    public List<StudyEntity> getStudies() {
        return StudyEntity.listAll();
    }


    @GET
    @Path("test")
    public String test() {
        return "blabla";
    }
}
