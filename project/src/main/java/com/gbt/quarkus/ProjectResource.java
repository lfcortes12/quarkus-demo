package com.gbt.quarkus;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/project")
public class ProjectResource {

    Logger log = LoggerFactory.getLogger(ProjectResource.class);


    @Timed(name = "getAllProjectsTimer", unit = MetricUnits.MILLISECONDS)
    @Counted(name = "getAllProjectsCounter")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Project> getAllProjects() {
        log.debug("Getting projects");
        return Project.listAll();
    }

    @Transactional
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(final Project newProject) {
        log.debug("Creating a project");
        newProject.persist();
    }

}