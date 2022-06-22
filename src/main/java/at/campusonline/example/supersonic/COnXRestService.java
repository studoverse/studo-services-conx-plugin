package at.campusonline.example.supersonic;

import at.campusonline.pub.auth.api.rest.impl.AuthSessionRestService;
import at.campusonline.pub.auth.api.rest.impl.AuthnRestService;
import at.campusonline.pub.desktop.rest.MyDesktopRestService;
import at.campusonline.pub.i18n.api.rest.TranslationsRestService;
import at.campusonline.pub.jaxrs.status.VersionApi;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Path;

@Path("/")
public class COnXRestService {

  @Inject
  MyDesktopRestService myDesktopRestService;

  @Inject
  AuthSessionRestService authSessionRestService;

  @Inject
  AuthnRestService authnRestService;

  @Inject
  TranslationsRestService translationsRestService;

  @Inject
  VersionApi versionApi;

  @PermitAll
  @Path("/user/desktop")
  public MyDesktopRestService getMyDesktopRestService() {
    return myDesktopRestService;
  }

  @PermitAll
  @Path("/auth/session")
  public AuthSessionRestService getAuthSessionRestService() {
    return authSessionRestService;
  }

  @PermitAll
  @Path("/auth/authn")
  public AuthnRestService getAuthnRestService() {
    return authnRestService;
  }

  @PermitAll
  @Path("/translations")
  public TranslationsRestService getTranslationsRestService() {
    return translationsRestService;
  }

  @PermitAll
  @Path("/version")
  public VersionApi getVersionApi() {
    return versionApi;
  }

}
