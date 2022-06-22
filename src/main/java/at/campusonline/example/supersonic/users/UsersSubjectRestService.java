package at.campusonline.example.supersonic.users;

import at.campusonline.pub.auth.api.subject.SecuritySubject;
import at.campusonline.pub.auth.rest.api.roles.rolesofidentities.AuthRolesOfIdentitySetResource;
import at.campusonline.pub.shared.rest.api.core.Cached;
import at.campusonline.pub.shared.rest.api.core.error.ErrorResponseBuilder;
import io.quarkus.security.Authenticated;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Michael Lorenzoni <m.lorenzoni@tugraz.at>
 */
@RequestScoped
@Path("/users/subject")
@Produces(MediaType.APPLICATION_JSON)
public class UsersSubjectRestService {

  @Inject
  SecuritySubject subject;

  @GET
  @Authenticated
  @Produces(MediaType.APPLICATION_JSON)
  public Cached<AuthRolesOfIdentitySetResource> getSubject() {

    return subject.getRawRolesOfIdentity()
            .orElseThrow(() -> new BadRequestException(
                    ErrorResponseBuilder.create()
                            .title("UNKNOWN_USER")
                            .detail("I do not know who you are.")
                            .build()));
  }

}
