package com.gbt.quarkus.resource;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/home")
public class Home {

    @Inject
    Template home;


    @GET
    @Path("")
    public TemplateInstance home() {

        return home.instance();
    }
}
