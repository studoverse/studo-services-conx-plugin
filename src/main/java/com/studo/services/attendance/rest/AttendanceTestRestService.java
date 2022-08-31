package com.studo.services.attendance.rest;

import at.campusonline.pub.auth.api.jaxrs.UserSessionDisabled;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

// disable the user session to allow machine to machine communication
@UserSessionDisabled
@Produces("application/json")
@Path("rest/attendance")
public class AttendanceTestRestService {

  private static final Logger logger = Logger.getLogger(AttendanceTestRestService.class);

  @GET
  @Path("hello-world")
  public String helloWorld() {
    return "hello world on " + co_public_api_url;
  }

  @ConfigProperty(name = "ENV_STUDO_SERVICES_DEBUG_MODE")
  Boolean debug;
  @ConfigProperty(name = "ENV_STUDO_SERVICES_TOKEN_SECRET")
  String token_secret;
  @ConfigProperty(name = "ENV_STUDO_SERVICES_DAL_BASE_URL")
  String dal_base_url;
  @ConfigProperty(name = "ENV_CONX_PUBLIC_API_URL")
  String co_public_api_url;

  @GET
  @Path("debug")
  public String debug() {
    if (!debug) return "Please enable debug mode in the guid.yaml";

    logger.warn("studo-service.token-secret: " + token_secret);
    //logger.warn("studo-service.backend-client-secret: " + client_secret);
    logger.warn("studo-service.dal-base-url: " + dal_base_url);

    return "Logged ENV vars";
  }

  @GET
  @Path("secure-hello-world")
  @RolesAllowed("studo-services.read")
  public String secureHelloWorld() {
    return "secure hello world on " + co_public_api_url;
  }

  private Boolean
}
