package com.studo.services.dal;

import at.campusonline.pub.auth.api.jaxrs.UserSessionDisabled;
import io.smallrye.jwt.auth.principal.DefaultJWTParser;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@UserSessionDisabled
@Produces("application/json")
@Path("rest/temporary-dal-endpoint")
public class TemporaryDalEndpoint {

  @ConfigProperty(name = "ENV_STUDO_SERVICES_TOKEN_SECRET")
  String secret;

  /**
   * ensure the secret has the right length
   */
  private String getSecret() {
    return secret.substring(0,32);
  }

  @GET
  public DalIdentityResource redirectTarget(@QueryParam("token") String token) throws ParseException {
    JWTParser parser = new DefaultJWTParser();
    JsonWebToken decryptedJwt = parser.verify(token, getSecret());
    return new DalIdentityResource(decryptedJwt.getSubject(), decryptedJwt.getName());
  }

}
