package at.campusonline.example.supersonic;

import at.campusonline.example.supersonic.model.GreetingListResource;
import at.campusonline.example.supersonic.model.GreetingResource;
import at.campusonline.example.supersonic.persistence.GreetingEntity;
import at.campusonline.example.supersonic.persistence.GreetingRepository;
import at.campusonline.pub.auth.api.context.SecurityContext;
import at.campusonline.pub.auth.api.role.ContextRole;
import at.campusonline.pub.auth.api.subject.SecuritySubject;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.util.stream.Collectors;

@RequestScoped
@Path(GreetingRestService.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class GreetingRestService {

  public final static String PATH = "/greetings";

  @Inject
  GreetingRepository repository;

  @GET
  public GreetingListResource getGreetings() {

    return new GreetingListResource(repository.findAll().stream()
            .map(this::mapToResource).collect(Collectors.toList()));
  }

  @GET
  @Path("/{example-id}")
  public GreetingResource getGreeting(@PathParam("example-id") BigInteger exampleId) {

    return repository.findById(exampleId)
            .map(this::mapToResource)
            .orElseThrow(() -> new NotFoundException("can not find example with specified id"));
  }

  private GreetingResource mapToResource(GreetingEntity entity) {
    GreetingResource resource = new GreetingResource();
    resource.setId(entity.getId());
    resource.setText(entity.getText());
    return resource;
  }

}

