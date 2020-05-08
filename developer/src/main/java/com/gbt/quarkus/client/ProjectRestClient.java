package com.gbt.quarkus.client;

import com.gbt.quarkus.client.dto.ProjectDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.List;

/**
 * Rest Client to retrieve projects from Project Service.
 * Configuration for this client is in the application.properties file
 * com.gbt.quarkus.client.ProjectRestClient.*
 *
 */
@Path("project")
@RegisterRestClient
public interface ProjectRestClient {

    @GET
    @Path("/")
    public List<ProjectDTO> getAllProjects();

}
