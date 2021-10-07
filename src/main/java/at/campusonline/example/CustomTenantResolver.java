package at.campusonline.example;

import io.quarkus.oidc.TenantResolver;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomTenantResolver implements TenantResolver {

  @Override
  public String resolve(RoutingContext context) {
    return "service";
  }

}
