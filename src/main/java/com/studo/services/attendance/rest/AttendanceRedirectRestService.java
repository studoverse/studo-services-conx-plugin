package com.studo.services.attendance.rest;

import at.campusonline.pub.auth.api.authinfo.AuthInfo;
import at.campusonline.pub.auth.api.jaxrs.UserSessionDisabled;
import at.campusonline.pub.auth.api.subject.SecuritySubject;
import at.campusonline.pub.auth.rest.api.identities.AuthIdentityResource;
import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Produces("application/json")
@UserSessionDisabled
@Path("attendance/redirect")
public class AttendanceRedirectRestService {

  private static final String TOKEN_PARAMETER = "token";

  @Inject
  SecuritySubject subject;

  @Inject
  AuthInfo authInfo;

  @Inject
  JsonWebToken token;

  @ConfigProperty(name = "studo-service.token-secret")
  String secret;

  @ConfigProperty(name = "studo-service.dal-base-url")
  String dalBaseUrl;

  /**
   * ensure the secret has the right length
   */
  private String getSecret() {
    return secret.substring(0,32);
  }

  /**
   * We use a signed token, so the studo dal application can verify the token.
   */
  private String getEncryptedStudoServiceToken() {
    if (token.getRawToken() == null) {
      return "anonymous";
    }

    return Jwt.subject(subject.getObfuscatedIdentityId())
            .preferredUserName(subject.getIdentity().getName())
            .jws()
            .signWithSecret(getSecret());
  }

  /**
   * Redirect to the studo dal endpoint.
   */
  @GET
  public Response redirect() {

    if (authInfo.isAnonymous()) {
      return Response.ok("please login").build();
    }

    AuthIdentityResource identity = subject.getIdentity();

    // this uri redirect to the studo dal application
    // currently we use a fake endpoint to verify data is transported correctly
    URI uri = UriBuilder.fromUri(dalBaseUrl)
            .queryParam(TOKEN_PARAMETER, getEncryptedStudoServiceToken())
            .build();

    return Response.temporaryRedirect(uri).build();
  }

}
