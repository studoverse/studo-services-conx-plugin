package com.studo.services.supersonic.model;

public class RoleExampleResource {

  boolean exampleRead;

  public RoleExampleResource(boolean exampleRead) {
    this.exampleRead = exampleRead;
  }

  public boolean isExampleRead() {
    return exampleRead;
  }

  public void setExampleRead(boolean exampleRead) {
    this.exampleRead = exampleRead;
  }
}