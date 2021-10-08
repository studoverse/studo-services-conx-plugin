package com.studo.services.attendance.rest;

import at.campusonline.pub.auth.api.jaxrs.UserSessionDisabled;
import com.studo.services.attendance.dto.AttendanceDto;
import com.studo.services.attendance.service.CourseService;
import com.studo.services.attendance.service.OrganisationService;
import com.studo.services.attendance.service.StudentService;
import com.studo.services.attendance.service.UserService;
import org.jboss.resteasy.annotations.GZIP;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@GZIP
@ApplicationScoped
@UserSessionDisabled
@Produces("application/json")
@Path("attendance")
public class AttendanceResource {

    @Inject
    StudentService studentService;

    @Inject
    OrganisationService organisationService;

    @Inject
    CourseService courseService;

    @Inject
    UserService userService;

    @GET
    public AttendanceDto getAttendance() {
        return new AttendanceDto(
                organisationService.getOrganisations(),
                courseService.getCourses(),
                studentService.getStudents(),
                userService.getUsers()
        );
    }

    @GET
    @Path("test")
    public String test() {
        return "blabla";
    }
}
