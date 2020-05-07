package com.gbt.quarkus;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/project")
public class ProjectResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PanacheEntityBase> getAllProjects() {
        return Project.listAll();
    }
}