package com.gbt.quarkus;

import com.gbt.quarkus.client.ProjectRestClient;
import com.gbt.quarkus.client.dto.ProjectDTO;
import com.gbt.quarkus.repository.DeveloperRepository;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/developer")
public class DeveloperResource {

    Logger log = LoggerFactory.getLogger(DeveloperResource.class);

    @Inject
    DeveloperRepository developerRepository;

    //matches with the HTML template that exist at /src/main/resources/templates/developer.html
    @Inject
    Template developer;

    @RestClient
    @Inject
    ProjectRestClient projectRestClient;

    @ConfigProperty(name = "welcome.message")
    String welcomeMessage;

    //this method is retried 2 times with a delay of 2secs in case of any error
    //fallbackHome method is executed then
    @Timed(name = "homeTimer", unit = MetricUnits.MILLISECONDS)
    @Counted(name = "homeCounter")
    @Fallback(fallbackMethod = "fallbackHome")
    @Retry(maxRetries = 2, delay = 2000)
    @GET
    @Path("/home")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance home() {
        log.debug("getting home");
        final var projects = projectRestClient.getAllProjects();
        log.debug("Retrieved project {}", projects);
        return developer.data("title", welcomeMessage)
                .data("developers", Developer.findAll().list())
                .data("projects",
                        projects);
    }

    //fallback method creates a dummy project instead of using the rest client
    public TemplateInstance fallbackHome() {
        log.debug("getting fallback home");
        final var defaultProject = new ProjectDTO("Internal Project", "GBT Internal Project");
        final var projects = List.of(defaultProject);
        return developer.data("title", welcomeMessage)
                .data("developers", Developer.findAll().list())
                .data("projects",
                        projects);
    }

    @Timed(name = "getAllDeveloperTimer", unit = MetricUnits.MILLISECONDS)
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllDevelopers() {
        return Response.ok(Developer.listAll()).build();
    }

    @Timed(name = "getByPlatformTimer", unit = MetricUnits.MILLISECONDS)
    @GET
    @Path("/platform/{platform}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Developer> getByPlatform(@PathParam("platform") final String platform) {
        return Developer.getByPlatform(platform);
    }

    @Timed(name = "getByPlatformInsensitiveCaseTimer", unit = MetricUnits.MILLISECONDS)
    @GET
    @Path("/platform/insensitive/{platform}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Developer> getByPlatformInsensitiveCase(@PathParam("platform") final String platform) {
        return Developer.getByPlatformNotCase(platform);
    }

    @Timed(name = "searchByPlatformTimer", unit = MetricUnits.MILLISECONDS)
    @GET
    @Path("/platform/search/platform/{platform}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Developer> searchByPlatform(@PathParam("platform") final String platform) {
        return Developer.searchByPlatform(platform);
    }

    @Timed(name = "searchByNameTimer", unit = MetricUnits.MILLISECONDS)
    @GET
    @Path("/platform/search/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Developer> searchByName(@PathParam("name") final String name) {
        return developerRepository.findByName(name);
    }

}