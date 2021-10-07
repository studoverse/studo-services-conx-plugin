package at.co.attendance.rest;

import at.co.attendance.dto.AttendanceDto;
import at.co.attendance.service.CourseService;
import at.co.attendance.service.OrganisationService;
import at.co.attendance.service.StudentService;
import at.co.attendance.service.UserService;
import org.jboss.resteasy.annotations.GZIP;

import javax.annotation.security.RolesAllowed;
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
}
