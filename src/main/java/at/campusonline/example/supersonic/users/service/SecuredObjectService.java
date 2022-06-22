package at.campusonline.example.supersonic.users.service;

import at.campusonline.pub.auth.api.context.SecurityContext;
import at.campusonline.pub.auth.api.object.GenericSecuredObject;
import at.campusonline.pub.auth.api.object.SecuredObject;

import javax.enterprise.context.Dependent;

@Dependent
public class SecuredObjectService {

  public SecuredObject getSecuredObjectWithOrgContext() {
    return GenericSecuredObject.createWithContext(SecurityContext.withOrgId("1"));
  }

  public SecuredObject getSecuredObjectWithMultipleContexts() {
    return GenericSecuredObject.createWithContexts(
            SecurityContext.withOrgId("1"),
            SecurityContext.withOrgId("10"));
  }

  public SecuredObject getSecuredObjectWithUnknownOrgContext() {
    return GenericSecuredObject.createWithContext(SecurityContext.withOrgId("UNKNOWN"));
  }
}
