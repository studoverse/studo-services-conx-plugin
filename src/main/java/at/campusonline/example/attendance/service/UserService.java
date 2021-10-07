package at.co.attendance.service;

import at.co.attendance.dto.CourseDto;
import at.co.attendance.dto.UserDto;
import at.co.attendance.entity.c02.IDPrefix;
import at.co.attendance.entity.function.FunctionEntity;
import at.co.attendance.entity.organisation.OrganisationEntity;
import at.co.attendance.mapper.UserDtoMapper;
import at.co.attendance.repository.FunctionRepository;
import at.co.attendance.repository.OrganisationRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class UserService {

    @Inject
    CourseService courseService;

    @Inject
    FunctionRepository functionRepository;

    @Inject
    OrganisationRepository organisationRepository;

    public List<UserDto> getUsers() {
        List<CourseDto> courses = courseService.getCourses();

        List<BigDecimal> orgIDs = courses.stream()
                .map(courseDto -> courseDto.organizationId).distinct().collect(Collectors.toList());

        List<OrganisationEntity> organisationEntities = organisationRepository.getOrganisationEntities(orgIDs);

        List<FunctionEntity> functionEntities = getFunctionEntities();
        List<UserDto> userDtos = organisationEntities.stream().filter(organisationEntity -> organisationEntity.getAllParents().size() > 0)
                .flatMap(organisationEntity -> UserDtoMapper.getUserDtos(organisationEntity.getAllParents(), functionEntities).stream())
                .distinct().collect(Collectors.toList());

        List<UserDto> collect2 = getUserDtos(courses);

        return Stream.concat(userDtos.stream(), collect2.stream())
                .filter(UserDtoMapper.distinctByKey(userDto ->  userDto.userId)).distinct().collect(Collectors.toList());
    }

    private List<UserDto> getUserDtos(List<CourseDto> courses) {
        List<UserDto> userDtos = courses.stream()
                .flatMap(courseDto ->
                        courseDto.groups.stream()
                                .flatMap(courseGroupDto -> courseGroupDto.internLecturers.stream()))
                .distinct().collect(Collectors.toList());
        IDPrefix idPrefix = (IDPrefix) IDPrefix.listAll().stream().findFirst().orElse(new IDPrefix());
        return UserDtoMapper.updateStudentId(courses, userDtos, idPrefix);
    }

    private List<FunctionEntity> getFunctionEntities() {
        List<FunctionEntity> functionEntities = functionRepository.listAll();
        return functionEntities.stream().filter(functionEntity ->
                        ofNullable(functionEntity).map(functionEntity1 -> functionEntity1.staffEntity).isPresent()).collect(Collectors.toList());
    }

}
