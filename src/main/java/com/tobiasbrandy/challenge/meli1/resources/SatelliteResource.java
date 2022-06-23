package com.tobiasbrandy.challenge.meli1.resources;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.node.TextNode;
import com.tobiasbrandy.challenge.meli1.resources.dtos.SplitSatelliteComDto;
import com.tobiasbrandy.challenge.meli1.services.SatelliteService;

@Controller
@Produces(MediaType.APPLICATION_JSON)
@Singleton
@Path("/")
public class SatelliteResource {

    private final SatelliteService satelliteService;

    @Inject
    public SatelliteResource(
        final SatelliteService satelliteService
    ) {
        this.satelliteService = Objects.requireNonNull(satelliteService);
    }

    @GET
    @Path("/")
    public TextNode helloWorld() {
        return TextNode.valueOf("MainResource says: Hello World!");
    }

    @POST
    @Path("/topsecret_split/{satellite_name}")
    public void createSatelliteCom(
        @PathParam("satellite_name")    final String                satellite_name,
                                        final SplitSatelliteComDto  satelliteCom
    ) {
        satelliteService.createSatelliteCom(satellite_name, satelliteCom.getDistance(), satelliteCom.getMessage());
    }

    @PUT
    @Path("/topsecret_split/{satellite_name}")
    public void updateSatelliteCom(
        @PathParam("satellite_name")    final String                satellite_name,
                                        final SplitSatelliteComDto  satelliteCom
    ) {
        satelliteService.updateSatelliteCom(satellite_name, satelliteCom.getDistance(), satelliteCom.getMessage());
    }
}
