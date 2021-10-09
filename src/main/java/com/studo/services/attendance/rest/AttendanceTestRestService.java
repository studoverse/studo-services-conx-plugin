package com.studo.services.attendance.rest;

import at.campusonline.pub.auth.api.jaxrs.UserSessionDisabled;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

// disable the user session to allow machine to machine communication
@UserSessionDisabled
@Produces("application/json")
@Path("rest/attendance")
public class AttendanceTestRestService {

  @GET
  @Path("hello-world")
  public String helloWorld() {
    return "hello world";
  }

  @GET
  @Path("secure-hello-world")
  @RolesAllowed("studo-services.read")
  public String secureHelloWorld() {
    return "secure hello world";
  }

}
