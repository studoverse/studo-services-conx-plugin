package at.campusonline.example.supersonic.greetings;


import at.campusonline.example.supersonic.greetings.model.GreetingListResource;
import at.campusonline.example.supersonic.greetings.model.GreetingResource;
import at.campusonline.example.supersonic.greetings.service.GreetingService;
import at.campusonline.pub.auth.api.jaxrs.UserSessionDisabled;
import io.quarkus.security.identity.SecurityIdentity;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;

@RequestScoped
@UserSessionDisabled
@Path("/rest/greetings")
@Produces(MediaType.APPLICATION_JSON)
public class ServerGreetingRestService {

  public static final String SCOPE_READ_ALL = "co-supersonic-all.read";

  @Inject
  GreetingService service;

  @Inject
  SecurityIdentity securityIdentity;

  @GET
  @RolesAllowed(SCOPE_READ_ALL)
  public GreetingListResource getGreetings() {
    return service.getGreetings();
  }

  @GET
  @Path("/{example-id}")
  @RolesAllowed(SCOPE_READ_ALL)
  public GreetingResource getGreeting(@PathParam("example-id") BigInteger exampleId) {

    return service.getGreeting(exampleId);
  }

}

