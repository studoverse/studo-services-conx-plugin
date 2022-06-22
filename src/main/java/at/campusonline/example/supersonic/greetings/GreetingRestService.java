package at.campusonline.example.supersonic.greetings;


import at.campusonline.example.supersonic.greetings.model.GreetingListResource;
import at.campusonline.example.supersonic.greetings.model.GreetingResource;
import at.campusonline.example.supersonic.greetings.service.GreetingService;
import io.quarkus.security.Authenticated;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;

@RequestScoped
@Path("/greetings")
@Produces(MediaType.APPLICATION_JSON)
public class GreetingRestService {

  @Inject
  GreetingService service;

  @GET
  @Authenticated
  public GreetingListResource getGreetings() {
    return service.getGreetings();
  }

  @GET
  @Authenticated
  @Path("/{example-id}")
  public GreetingResource getGreeting(@PathParam("example-id") BigInteger exampleId) {
    return service.getGreeting(exampleId);
  }

}
