package com.studo.services;

import io.quarkus.oidc.TenantResolver;
import io.vertx.ext.web.RoutingContext;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomTenantResolver implements TenantResolver {

  @Override
  public String resolve(RoutingContext context) {
    if (context.request().path().startsWith("/studo/services/api/attendance")) {
      return "service";
    }
    return "user";
  }

}
