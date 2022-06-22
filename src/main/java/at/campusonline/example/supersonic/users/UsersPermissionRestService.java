package at.campusonline.example.supersonic.users;


import at.campusonline.example.supersonic.users.model.PermissionResource;
import at.campusonline.example.supersonic.users.service.SecuredObjectService;
import at.campusonline.pub.auth.api.context.SecurityContext;
import at.campusonline.pub.auth.api.guard.SecurityGuard;
import at.campusonline.pub.auth.api.judge.RoleJudge;
import at.campusonline.pub.auth.api.object.SecuredObject;
import at.campusonline.pub.auth.api.role.ContextRole;
import at.campusonline.pub.auth.api.subject.SecuritySubject;
import io.quarkus.security.Authenticated;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@RequestScoped
@Path("/users/permissions")
@Produces(MediaType.APPLICATION_JSON)
public class UsersPermissionRestService {

  private final static String EXAMPLES_READ = "EXAMPLES/READ";
  private final static String PERMISSION_FOR_APP_IN_CONTEXT = "READ permission for app EXAMPLES in org CONTEXT";
  private final static String PERMISSION_FOR_OBJECT_IN_CONTEXT = "READ permission for generic OBJECT in org CONTEXT";

  @Inject
  SecuritySubject subject;

  @Inject
  RoleJudge roleJudge;

  @Inject
  SecurityGuard guard;

  @Inject
  SecuredObjectService securedObjectService;

  @GET
  @Authenticated
  @Path("/read-access-for-app")
  public PermissionResource getReadPermissionForApp(
          @NotNull @QueryParam("org_id") String orgId) {

    SecurityContext context = SecurityContext.withOrgId(orgId);

    if (!subject.hasRole(ContextRole.createRole(context, EXAMPLES_READ))) {
      return new PermissionResource(PERMISSION_FOR_APP_IN_CONTEXT, false);
    }

    return new PermissionResource(PERMISSION_FOR_APP_IN_CONTEXT, true);
  }

  @GET
  @Authenticated
  @Path("/read-permission-for-object/with-org-ctx")
  public PermissionResource getReadPermissionForObjectWithOrgCtx() {

    SecuredObject object = securedObjectService.getSecuredObjectWithOrgContext();

    return new PermissionResource(PERMISSION_FOR_OBJECT_IN_CONTEXT,
            roleJudge.judge(subject, object).isInRole(EXAMPLES_READ));
  }

  @GET
  @Authenticated
  @Path("/read-permission-for-object/with-multiple-org-ctx")
  public PermissionResource getReadPermissionForObjectWithMultipleOrgCtx() {

    SecuredObject object = securedObjectService.getSecuredObjectWithMultipleContexts();

    return new PermissionResource(PERMISSION_FOR_OBJECT_IN_CONTEXT,
            roleJudge.judge(subject, object).isInRole(EXAMPLES_READ));
  }

  @GET
  @Authenticated
  @Path("/read-permission-for-object/with-unknown-org-ctx")
  public PermissionResource getReadPermissionForObjectWithUnknownOrgCtx() {

    SecuredObject object = securedObjectService.getSecuredObjectWithUnknownOrgContext();

    return new PermissionResource(PERMISSION_FOR_OBJECT_IN_CONTEXT,
            roleJudge.judge(subject, object).isInRole(EXAMPLES_READ));
  }

  @GET
  @Authenticated
  @Path("/secured-object-with-org-ctx")
  public PermissionResource getSecuredObjectWithOrgCtx() {
    SecuredObject object = securedObjectService.getSecuredObjectWithOrgContext();
    guard.check(() -> roleJudge.judge(subject, object).isInRole(EXAMPLES_READ));
    return new PermissionResource(PERMISSION_FOR_OBJECT_IN_CONTEXT, true);
  }

  @GET
  @Authenticated
  @Path("/secured-object-with-unknown-org-ctx")
  public PermissionResource getSecuredObjectWithUnknown() {
    SecuredObject object = securedObjectService.getSecuredObjectWithUnknownOrgContext();
    guard.check(() -> roleJudge.judge(subject, object).isInRole(EXAMPLES_READ));
    return new PermissionResource(PERMISSION_FOR_OBJECT_IN_CONTEXT, true);
  }

}
