package com.studo.services.dal;

public class DalIdentityResource {

  private String identityUid;
  private String name;

  public DalIdentityResource() {
  }

  public DalIdentityResource(String identityUid, String name) {
    this.identityUid = identityUid;
    this.name = name;
  }

  public String getIdentityUid() {
    return identityUid;
  }

  public void setIdentityUid(String identityUid) {
    this.identityUid = identityUid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
