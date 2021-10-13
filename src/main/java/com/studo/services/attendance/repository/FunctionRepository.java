package com.studo.services.attendance.repository;

import com.studo.services.attendance.entity.function.FunctionEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Arbër Gjergjizi <arber.gjergjizi@campus02.at>
 */
@ApplicationScoped
public class FunctionRepository implements PanacheRepository<FunctionEntity> {
    public List<FunctionEntity> getFunctionEntities(List<BigDecimal> orgIds){
        return list("organisationId in ?1", orgIds);
    }
}
