package com.studo.services.supersonic;

import at.campusonline.pub.auth.api.jaxrs.UserSessionDisabled;
import com.studo.services.supersonic.model.GreetingListResource;
import com.studo.services.supersonic.model.GreetingResource;
import com.studo.services.supersonic.persistence.GreetingEntity;
import com.studo.services.supersonic.persistence.GreetingRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;
import java.util.stream.Collectors;

@RequestScoped
@UserSessionDisabled
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

