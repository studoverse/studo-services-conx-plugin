package at.campusonline.example.supersonic.users;

import at.campusonline.pub.shared.rest.api.base.UserSession;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Michael Lorenzoni <m.lorenzoni@tugraz.at>
 */
@RequestScoped
@Path("/users/session")
@Produces(MediaType.APPLICATION_JSON)
public class UsersSessionRestService {

  @Inject
  UserSession session;

  @GET
  @PermitAll
  public UserSession getSession() {
    return session;
  }

}
