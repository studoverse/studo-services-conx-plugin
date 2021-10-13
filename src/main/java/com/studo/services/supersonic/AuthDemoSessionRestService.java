package com.studo.services.supersonic;

import at.campusonline.pub.shared.rest.api.base.Session;

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
@Path("/auth-demo/session")
@Produces(MediaType.APPLICATION_JSON)
public class AuthDemoSessionRestService {

  @Inject
  Session session;

  @GET
  @PermitAll
  public Session getSession() {
    return session;
  }

}
