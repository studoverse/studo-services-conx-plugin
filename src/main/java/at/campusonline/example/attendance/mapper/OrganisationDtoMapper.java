package at.co.attendance.mapper;

import at.co.attendance.dto.OrganisationDto;
import at.co.attendance.entity.course.CourseEntity;
import at.co.attendance.entity.function.FunctionEntity;
import at.co.attendance.entity.organisation.OrganisationEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
public class OrganisationDtoMapper {

    public static OrganisationDto mapOrganisationDto(OrganisationEntity organisationEntity,
                                                     List<CourseEntity> courseEntities,
                                                     List<FunctionEntity> functionEntities) {
        return new OrganisationDto(organisationEntity.id,
                organisationEntity.name,
                courseEntities.stream()
                        .flatMap(courseEntity -> getUsers(getAllParents(courseEntity), functionEntities)
                                .stream()).distinct().collect(Collectors.toList())
        );
    }

    private static List<OrganisationEntity> getAllParents(CourseEntity courseEntity) {
        List<OrganisationEntity> organisationEntities = courseEntity.organisationEntity.getAllParents();
        organisationEntities.add(courseEntity.organisationEntity);
        return organisationEntities;
    }

    private static List<BigDecimal> getUsers(List<OrganisationEntity> allParents, List<FunctionEntity> functionEntities) {
        if (allParents.size() == 0) return new ArrayList<>();
        var filteredFunctionEntities = functionEntities.stream().filter(functionEntity ->
                        allParents.stream().anyMatch(organisationEntity -> organisationEntity.id.equals(functionEntity.organisationEntity.id))).collect(Collectors.toList());

        return filteredFunctionEntities.stream().map(functionEntity -> functionEntity.staffEntity.id).distinct().collect(Collectors.toList());
    }
}
