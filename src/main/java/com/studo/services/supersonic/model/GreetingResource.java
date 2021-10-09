package com.studo.services.supersonic.model;

import java.math.BigInteger;

/**
 * @author Michael Lorenzoni <m.lorenzoni@tugraz.at>
 */
public class GreetingResource {

  private BigInteger id;

  private String text;

  public BigInteger getId() {
    return id;
  }

  public void setId(final BigInteger id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
