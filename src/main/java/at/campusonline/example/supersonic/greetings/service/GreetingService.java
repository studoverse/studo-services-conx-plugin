package at.campusonline.example.supersonic.greetings.service;

import at.campusonline.example.supersonic.greetings.model.GreetingListResource;
import at.campusonline.example.supersonic.greetings.model.GreetingResource;
import at.campusonline.example.supersonic.greetings.persistence.GreetingEntity;
import at.campusonline.example.supersonic.greetings.persistence.GreetingRepository;
import at.campusonline.pub.shared.rest.api.core.error.ErrorResponseBuilder;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.math.BigInteger;
import java.util.stream.Collectors;

@Dependent
public class GreetingService {

  @Inject
  GreetingRepository repository;

  public GreetingListResource getGreetings() {

    return new GreetingListResource(repository.findAll().stream()
            .map(this::mapToResource).collect(Collectors.toList()));
  }

  public GreetingResource getGreeting(BigInteger exampleId) {

    return repository.findById(exampleId)
            .map(this::mapToResource)
            .orElseThrow(() -> new BadRequestException(
                    ErrorResponseBuilder.create()
                            .title("CAN_NOT_FIND_GREETING_WITH_SPECIFIED_ID")
                            .detail("Can not find greeting with specified id.")
                            .build()));
  }

  private GreetingResource mapToResource(GreetingEntity entity) {
    GreetingResource resource = new GreetingResource();
    resource.setId(entity.getId());
    resource.setText(entity.getText());
    return resource;
  }

}
