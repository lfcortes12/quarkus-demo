package com.gbt.quarkus;

import com.gbt.quarkus.repository.DeveloperRepository;
import com.gbt.quarkus.service.DeveloperService;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/developer")
public class DeveloperResource {

    @Inject
    DeveloperService developerService;

    @Inject
    DeveloperRepository developerRepository;

    @Inject
    Template developer;


    @GET
    @Path("/home")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance home() {
        return developer.data("title", "Globant Devs").data("developers", Developer.findAll().list());
    }



    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Developer> getAllPerson() {
        return Developer.listAll();
    }

    @GET
    @Path("/platform/{platform}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Developer> getByPlatform(@PathParam("platform") String platform) {
        return Developer.getByPlatform(platform);
    }

    @GET
    @Path("/platform/insensitive/{platform}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Developer> getByPlatformInsensitiveCase(@PathParam("platform") String platform) {
        return Developer.getByPlatformNotCase(platform);
    }

    @GET
    @Path("/platform/search/platform/{platform}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Developer> searchByPlatform(@PathParam("platform") String platform) {
        return Developer.searchByPlatform(platform);
    }


    @GET
    @Path("/platform/search/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Developer> searchByName(@PathParam("name") String name) {
        return developerRepository.findByName(name);
    }

}