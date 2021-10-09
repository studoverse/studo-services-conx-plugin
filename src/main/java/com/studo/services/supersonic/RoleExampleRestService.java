package com.studo.services.supersonic;

import com.studo.services.supersonic.model.RoleExampleResource;
import at.campusonline.pub.auth.api.context.SecurityContext;
import at.campusonline.pub.auth.api.guard.SecurityGuard;
import at.campusonline.pub.auth.api.judge.RoleJudge;
import at.campusonline.pub.auth.api.object.GenericSecuredObject;
import at.campusonline.pub.auth.api.object.SecuredObject;
import at.campusonline.pub.auth.api.role.ContextRole;
import at.campusonline.pub.auth.api.subject.SecuritySubject;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path(RoleExampleRestService.PATH)
@Produces(MediaType.APPLICATION_JSON)
public class RoleExampleRestService {

  public final static String PATH = "/role-examples";

  @Inject
  SecuritySubject subject;

  @Inject
  RoleJudge roleJudge;

  @Inject
  SecurityGuard guard;

  @GET
  public RoleExampleResource getRoleExampleResource(@NotNull @QueryParam("org-id") String orgId) {

    SecurityContext context = SecurityContext.withOrgId(orgId);

    RoleExampleResource roleExampleResource = new RoleExampleResource(
            subject.hasRole(ContextRole.createRole(context, "EXAMPLES/READ")));

    return roleExampleResource;
  }

  @GET
  @Path("/check-demo-1")
  public String getRoleExampleResourceCheckDemo1(@NotNull @QueryParam("org-id") String orgId) {

    SecurityContext context = SecurityContext.withOrgId(orgId);

    if (!subject.hasRole(ContextRole.createRole(context, "EXAMPLES/READ"))) {
      throw new ForbiddenException("you have no permission");
    }

    return "has access";

  }

  @GET
  @Path("/check-demo-2")
  public String getRoleExampleResourceCheckDemo2(@NotNull @QueryParam("org-id") String orgId) {

    SecuredObject object = GenericSecuredObject.createWithContext(SecurityContext.withOrgId(orgId));
    guard.check(() -> roleJudge.judge(subject, object).isInRole("EXAMPLES/READ"));

    return "has access";
  }


}
