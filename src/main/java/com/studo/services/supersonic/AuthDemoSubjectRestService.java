package com.studo.services.supersonic;

import at.campusonline.pub.auth.api.subject.SecuritySubject;
import at.campusonline.pub.auth.rest.api.roles.rolesofidentities.AuthRolesOfIdentitySetResource;
import at.campusonline.pub.shared.rest.api.core.Cached;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author Michael Lorenzoni <m.lorenzoni@tugraz.at>
 */
@RequestScoped
@Path("/auth-demo/subject")
@Produces(MediaType.APPLICATION_JSON)
public class AuthDemoSubjectRestService {

  @Inject
  SecuritySubject subject;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Cached<AuthRolesOfIdentitySetResource> getSubject() {

    return subject.getRawRolesOfIdentity()
            .orElseThrow(() -> new NotFoundException("I do not know who you are"));
  }

}
