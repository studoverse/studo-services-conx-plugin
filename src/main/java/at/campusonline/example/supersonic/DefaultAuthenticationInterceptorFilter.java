package at.campusonline.example.supersonic;

import at.campusonline.pub.auth.api.oidc.dad.hack.quarkus.AuthenticationInterceptorFilter;
import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;

/**
 * @author Michael Lorenzoni <m.lorenzoni@tugraz.at>
 */
public class DefaultAuthenticationInterceptorFilter {

  @ConfigProperty(name = "conx.application-path", defaultValue = "/")
  String prefixPath;

  @Inject
  AuthenticationInterceptorFilter filter;

  @RouteFilter(1000)
  public void interceptAuthentication(RoutingContext rc) {

    filter.interceptAuthentication(prefixPath, rc);
  }

}
