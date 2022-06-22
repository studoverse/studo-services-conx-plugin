package at.campusonline.example.supersonic.greetings.persistence;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Dependent
public class GreetingRepository {

  @Inject
  public GreetingRepository(final EntityManager em) {
  }

  public Optional<GreetingEntity> findById(final BigInteger id) {

    return GreetingEntity.findByIdOptional(id);
  }

  public List<GreetingEntity> findAll() {

    return GreetingEntity.findAll().list();
  }

}
