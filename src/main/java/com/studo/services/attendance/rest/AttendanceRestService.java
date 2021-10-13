package com.studo.services.attendance.rest;

import at.campusonline.pub.auth.api.jaxrs.UserSessionDisabled;
import com.studo.services.attendance.dto.AttendanceDto;
import com.studo.services.attendance.dto.OrganisationDto;
import com.studo.services.attendance.entity.course.CourseEntity;
import com.studo.services.attendance.entity.function.FunctionEntity;
import com.studo.services.attendance.entity.study.StudyEntity;
import com.studo.services.attendance.service.OrganisationService;
import com.studo.services.attendance.service.CourseService;
import com.studo.services.attendance.service.UserService;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.annotations.GZIP;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    @ConfigProperty(name = "studo-service.token-secret")
    String secret;

    private void checkAuthorizationHeader(String secret) {
        if (!Objects.equals(secret, this.secret.substring(0,32))) {
            throw new SecurityException("Invalid authorization header");
        }
    }

    @GET
    public AttendanceDto getAttendance(
            @DefaultValue("1") @QueryParam(value = "orgId") List<BigDecimal> orgIds,
            @DefaultValue("2021/22") @QueryParam(value = "academicYear") String academicYear,
            @DefaultValue("W") @QueryParam(value = "semester") List<String> semesters,
            @DefaultValue("FT") @QueryParam(value = "resourceType") List<String> resourceTypes,
            @DefaultValue("A") @QueryParam(value = "eventType") List<String> eventTypes,
            @HeaderParam(value = "X-Authorization") String secret
    ) {
        checkAuthorizationHeader(secret);
        List<CourseEntity> courseEntities = courseService.getCourseEntities(orgIds, academicYear, semesters);

        return new AttendanceDto(
                courseService.getCourses(courseEntities, resourceTypes, eventTypes),
                userService.getStudents(courseEntities),
                userService.getStaff(courseEntities),
                userService.getIdentities(courseEntities)
        );
    }

    @GET
    @Path("functions")
    public List<FunctionEntity> getFunctions(
            @DefaultValue("1") @QueryParam(value = "orgId") List<BigDecimal> orgIds,
            @HeaderParam(value = "X-Authorization") String secret
    ) {
        checkAuthorizationHeader(secret);
        return userService.getFunctions(orgIds);
    }

    @GET
    @Path("organisations")
    public List<OrganisationDto> getOrganisations(
            @HeaderParam(value = "X-Authorization") String secret
    ) {
        checkAuthorizationHeader(secret);
        return organisationService.getOrganisations();
    }

    // slow CO Public views
    @GET
    @Path("studies")
    public List<StudyEntity> getStudies(
            @HeaderParam(value = "X-Authorization") String secret
    ) {
        checkAuthorizationHeader(secret);
        return StudyEntity.listAll();
    }

    @GET
    @Path("test")
    public String test(
            @HeaderParam(value = "X-Authorization") String secret
    ) {
        checkAuthorizationHeader(secret);
        return "blabla";
    }
}
