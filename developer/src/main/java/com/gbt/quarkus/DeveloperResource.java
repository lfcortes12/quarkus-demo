package com.gbt.quarkus;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gbt.quarkus.client.ProjectRestClient;
import com.gbt.quarkus.client.dto.ProjectDTO;
import com.gbt.quarkus.repository.DeveloperRepository;
import com.gbt.quarkus.service.DeveloperService;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

@Path("/developer")
public class DeveloperResource {

	Logger log = LoggerFactory.getLogger(DeveloperResource.class);

	@Inject
	DeveloperService developerService;

	@Inject
	DeveloperRepository developerRepository;

	@Inject
	Template developer;

	@RestClient
	@Inject
	ProjectRestClient projectRestClient;

	@ConfigProperty(name = "welcome.message")
	String welcomeMessage;

	@Fallback(fallbackMethod = "fallbackHome")
	@Retry(maxRetries = 2, delay = 5000)
	@GET
	@Path("/home")
	@Produces(MediaType.TEXT_HTML)
	public TemplateInstance home() {
		log.debug("getting home");
		final var projects = projectRestClient.getAllProjects();
		return developer.data("title", welcomeMessage).data("developers", Developer.findAll().list()).data("projects",
				projects);
	}

	public TemplateInstance fallbackHome() {
		log.debug("getting fallback home");
		final ProjectDTO defaultProject = new ProjectDTO("Internal Project", "GBT Internal Project");
		final var projects = List.of(defaultProject);
		return developer.data("title", welcomeMessage).data("developers", Developer.findAll().list()).data("projects",
				projects);
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllDevelopers() {
		return Response.ok(Developer.listAll()).build();
	}

	@GET
	@Path("/platform/{platform}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Developer> getByPlatform(@PathParam("platform") final String platform) {
		return Developer.getByPlatform(platform);
	}

	@GET
	@Path("/platform/insensitive/{platform}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Developer> getByPlatformInsensitiveCase(@PathParam("platform") final String platform) {
		return Developer.getByPlatformNotCase(platform);
	}

	@GET
	@Path("/platform/search/platform/{platform}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Developer> searchByPlatform(@PathParam("platform") final String platform) {
		return Developer.searchByPlatform(platform);
	}

	@GET
	@Path("/platform/search/name/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Developer> searchByName(@PathParam("name") final String name) {
		return developerRepository.findByName(name);
	}

}