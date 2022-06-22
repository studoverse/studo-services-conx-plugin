package at.campusonline.example.supersonic.greetings.model;

import java.math.BigInteger;

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
