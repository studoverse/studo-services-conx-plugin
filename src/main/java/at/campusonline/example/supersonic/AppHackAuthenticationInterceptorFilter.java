package at.campusonline.example.supersonic;

import at.campusonline.pub.auth.api.oidc.dad.hack.quarkus.HackAuthenticationInterceptorFilter;
import io.quarkus.vertx.web.RouteFilter;
import io.vertx.ext.web.RoutingContext;

import javax.inject.Inject;

/**
 * @author Michael Lorenzoni <m.lorenzoni@tugraz.at>
 */
public class AppHackAuthenticationInterceptorFilter {

  @Inject
  HackAuthenticationInterceptorFilter filter;

  @RouteFilter(1000)
  public void interceptAuthentication(RoutingContext rc) {
    filter.interceptAuthentication(rc);
  }

}
