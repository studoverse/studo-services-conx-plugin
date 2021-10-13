package com.studo.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath(Api.PATH)
public class Api extends Application {

  public final static String PATH = "/studo/services/api";

}
