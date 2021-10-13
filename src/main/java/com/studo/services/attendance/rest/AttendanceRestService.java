package com.studo.services.attendance.rest;

import at.campusonline.pub.auth.api.jaxrs.UserSessionDisabled;
import com.studo.services.attendance.dto.CoursesAndUsers;
import com.studo.services.attendance.dto.CourseDto;
import com.studo.services.attendance.dto.OrganisationDto;
import com.studo.services.attendance.dto.UsersDto;
import com.studo.services.attendance.entity.course.CourseEntity;
import com.studo.services.attendance.entity.function.FunctionEntity;
import com.studo.services.attendance.entity.identity.IdentityEntity;
import com.studo.services.attendance.entity.staff.StaffEntity;
import com.studo.services.attendance.entity.student.StudentEntity;
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
import java.util.stream.Collectors;

/**
 *
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@GZIP
@ApplicationScoped
// disable the user session to allow machine to machine communication
@UserSessionDisabled
//@RolesAllowed("studo-services.read")
@Produces("application/json")
@Path("rest/attendance")
public class AttendanceRestService {

    @Inject
    OrganisationService organisationService;

    @Inject
    CourseService courseService;

    @Inject
    UserService userService;

    @HeaderParam(value = "X-Authorization")
    String authorizationHeader;

    @ConfigProperty(name = "studo-service.token-secret")
    String secret; // mysecretmysecretmysecretmysecretmysecretmysecret for local developement

    private void checkAuthorizationHeader() {
        if (!Objects.equals(authorizationHeader, this.secret.substring(0,32))) {
            throw new SecurityException("Invalid authorization header");
        }
    }

    @GET
    @Path("coursesAndUsers") // Use either coursesAndUsers or users + courses
    public CoursesAndUsers getCoursesAndUsers(
            @DefaultValue("2021/22") @QueryParam(value = "academicYear") String academicYear,
            @DefaultValue("W") @QueryParam(value = "semester") List<String> semesters,
            @DefaultValue("FT") @QueryParam(value = "resourceType") List<String> resourceTypes,
            @DefaultValue("A") @QueryParam(value = "eventType") List<String> eventTypes
    ) {
        checkAuthorizationHeader();
        List<CourseEntity> courseEntities = courseService.getCourseEntities( academicYear, semesters);

        return new CoursesAndUsers(
                courseService.getCourses(courseEntities, resourceTypes, eventTypes),
                userService.getStudents(courseEntities),
                userService.getStaff(courseEntities),
                userService.getIdentities(courseEntities)
        );
    }

    @GET
    @Path("users") // max 999 elements
    public UsersDto getUsers(
            @QueryParam(value = "ids") List<String> ids,
            @QueryParam(value = "idName") String idName
    ) {
        checkAuthorizationHeader();
        if (!idName.equals("obfuscatedId") && !idName.equals("staffId") && !idName.equals("studentId")) {
            throw new IllegalArgumentException("Invalid idName"); // Don't allow sql injections and only allow filters with good performance
        }
        List<IdentityEntity> identities = IdentityEntity.list(idName + " in ?1", ids);
        List<StudentEntity> students = StudentEntity.list("id in ?1",
                identities.stream().map(identityEntity -> identityEntity.studentId).distinct().collect(Collectors.toList()));
        List<StaffEntity> staffs = StaffEntity.list("id in ?1",
                identities.stream().map(identityEntity -> identityEntity.staffId).distinct().collect(Collectors.toList()));

        return new UsersDto(identities, students, staffs);
    }

    /*
    ResourceTypes:
    FT,"{fix,confirmed,null,null}"
    WT,"{geplant,planned,null,null}"
    GT,"{ganztägig,all day,null,null}"
    FV,"{verschoben,rescheduled,null,null}"
    WA,"{abgelehnt,rejected,null,null}"
    FA,"{abgesagt,cancelled,null,null}"
    FG,"{gelöscht,deleted,null,null}"

    EventTypes:
    A,"{Abhaltung,regular class,null,null}"
    V,"{Vorbesprechung,preparatory class,null,null}"
    P,"{Prüfung (geplant),examination (planned),null,null}"
    AV,"{Aufbau u. Vorbereitung,installations & preparation,null,null}"
    N,"{Nachbereitung,follow-up maintenance,null,null}"
    PT,"{Prüfungstermin,exam date,null,null}"
    CBT,"{CBT-Abhaltung,CBT session,null,null}" // Appointment is managed by external software (med-uni graz/wien) ??
    A,"{Abhaltung,regular class,null,null}"
    VV,"{Vorbereitung,preparation,null,null}"
    VN,"{Nachbereitung,follow-up maintenance,null,null}"
    K,"{kein Ereignistyp,no type of event,null,null}" // ??
    F,"{Ferien,holidays,null,null}"
    FT,"{Feiertag(e),public holiday(s),null,null}"
    W,"{Wartung,maintenance,null,null}" / ??
    LT,"{LV-freier Tag,lecture-free day,null,null}"
    LS,"{LV-freie Stunden,lecture-free hours,null,null}"
    SPERRE,"{Sperre,lock,null,null}" // ??

    LV_STATUS_ALLE:  (comma separated string, dynamically calculated by database, might not be stable across CO-releases)
    GP,geplant
    GM,gemeldet
    NEU,neue LV durch LEH
    KOPIE,Kopie einer LV
    NEUPAR,neue Parallel LV
    BF,genehmigt // normal "released"
    AB,abgelehnt
    NEUGLV,Kopie einer LV im anderen Semester
    AG,abgehalten
    ZGK,zustandegekommen // ??
     */

    @GET
    @Path("courses")
    public List<CourseDto> getCourses(
            @DefaultValue("2021/22") @QueryParam(value = "academicYear") String academicYear,
            @DefaultValue("W") @QueryParam(value = "semester") List<String> semesters, // Either "W" or "S"
            @DefaultValue("FT") @QueryParam(value = "resourceType") List<String> resourceTypes,
            @DefaultValue("A") @QueryParam(value = "eventType") List<String> eventTypes
    ) {
        checkAuthorizationHeader();
        List<CourseEntity> courseEntities = courseService.getCourseEntities(academicYear, semesters);
        return courseService.getCourses(courseEntities, resourceTypes, eventTypes);
    }

    @GET
    @Path("functions")
    public List<FunctionEntity> getFunctions(
            @QueryParam(value = "functionTypes") List<String> functionTypes
    ) {
        checkAuthorizationHeader();
        return userService.getFunctions(functionTypes);
    }

    @GET
    @Path("allFunctionsSlow")
    public List<FunctionEntity> getAllFunctionsSlow() {
        checkAuthorizationHeader();
        return userService.getAllFunctionsSlow();
    }

    @GET
    @Path("organisations")
    public List<OrganisationDto> getOrganisations() {
        checkAuthorizationHeader();
        return organisationService.getOrganisations();
    }

    @GET
    @Path("studiesSlow") // Returns also studies from students who are not active students anymore
    public List<StudyEntity> getAllStudiesSlow() {
        checkAuthorizationHeader();
        return StudyEntity.listAll();
    }

    /*
    // status: ??
     */

    @GET
    @Path("studiesSlow")
    public List<StudyEntity> getStudies(
            @QueryParam("status") List<String> status
    ) {
        checkAuthorizationHeader();
        return StudyEntity.list("status in ?1", status);
    }

    @GET
    @Path("studies")
    public List<StudyEntity> getStudies(
            @QueryParam("studentIds") List<String> studentIds,
            @QueryParam("status") List<String> status
    ) {
        checkAuthorizationHeader();
        return StudyEntity.list("studentId in ?1 and status in ?2", studentIds, status);
    }

    @GET
    @Path("test")
    public String test() {
        checkAuthorizationHeader();
        return "blabla";
    }
}
