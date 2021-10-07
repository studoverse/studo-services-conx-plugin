package at.co.attendance.repository;

import at.co.attendance.entity.c02.FunctionFilter;
import at.co.attendance.entity.function.FunctionEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class FunctionRepository implements PanacheRepository<FunctionEntity> {
    public List<FunctionEntity> getFunctionEntities(){
        return list("KURZBEZEICHNUNG in ?1", getFunctionFilters());
    }
    public List<String> getFunctionFilters() {
        List<FunctionFilter> functionFilters = FunctionFilter.listAll();
        return functionFilters.stream().map(functionFilter -> functionFilter.functionType).collect(Collectors.toList());
    }
}
