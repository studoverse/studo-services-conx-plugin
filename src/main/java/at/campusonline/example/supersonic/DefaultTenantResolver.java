package at.campusonline.example.supersonic;

import io.quarkus.oidc.TenantResolver;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultTenantResolver implements TenantResolver {

  @Override
  public String resolve(RoutingContext context) {
    // every endpoint which supports machine to machine communication has to start with /rest/**
    if (context.request().path().startsWith("/studo/services/api/rest")) {
      return "service";
    }

    // otherwise, we expect a GUI is accessing our rest services
    return "user";
  }

}
