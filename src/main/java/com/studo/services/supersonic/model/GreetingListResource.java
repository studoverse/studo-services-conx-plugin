package com.studo.services.supersonic.model;

import at.campusonline.pub.shared.rest.api.core.ListResource;

import java.util.List;

public class GreetingListResource extends ListResource<GreetingResource> {

  public GreetingListResource(List<GreetingResource> items) {
    super(items);
  }

}
