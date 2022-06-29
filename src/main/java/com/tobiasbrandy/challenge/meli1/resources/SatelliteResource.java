package com.tobiasbrandy.challenge.meli1.resources;

import static com.tobiasbrandy.challenge.meli1.validation.ErrorCodes.*;
import static com.tobiasbrandy.challenge.meli1.validation.Validable.validOrFail;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.node.TextNode;
import com.tobiasbrandy.challenge.meli1.resources.dtos.input.SatellitePositionDto;
import com.tobiasbrandy.challenge.meli1.resources.dtos.output.PlainSatelliteComDto;
import com.tobiasbrandy.challenge.meli1.models.Satellite;
import com.tobiasbrandy.challenge.meli1.resources.dtos.input.SplitSatelliteComDto;
import com.tobiasbrandy.challenge.meli1.resources.dtos.output.PlainSatelliteDto;
import com.tobiasbrandy.challenge.meli1.services.SatelliteService;

@Produces(MediaType.APPLICATION_JSON)
@Singleton
@Path("/")
public class SatelliteResource {

    private final SatelliteService satelliteService;

    @Inject
    public SatelliteResource(final SatelliteService satelliteService) {
        this.satelliteService = Objects.requireNonNull(satelliteService);
    }

    @GET
    @Path("/")
    public TextNode helloWorld() {
        return TextNode.valueOf("SatelliteResource says: Hello World!");
    }

    @GET
    @Path("/satellites")
    public List<PlainSatelliteDto> listSatellites() {
        return PlainSatelliteDto.fromSatellites(satelliteService.listSatellites());
    }

    @GET
    @Path("/satellites/{name}")
    public PlainSatelliteDto findSatellite(@PathParam("name") final String name) {
        return satelliteService.findSatellite(name)
            .map(PlainSatelliteDto::fromSatellite)
            .orElseThrow(() -> satelliteNotFound(name))
            ;
    }

    @POST
    @Path("/satellites/{name}")
    public void createSatellite(@PathParam("name") final String name, final SatellitePositionDto satellitePosition) {
        validOrFail(satellitePosition);
        satelliteService.createSatellite(name, satellitePosition.positionX(), satellitePosition.positionY());
    }

    @PUT
    @Path("/satellites/{name}")
    public void updateSatellite(@PathParam("name") final String name, final SatellitePositionDto satellitePosition) {
        validOrFail(satellitePosition);
        satelliteService.updateSatellite(name, satellitePosition.positionX(), satellitePosition.positionY());
    }

    @DELETE
    @Path("/satellites/{name}")
    public void deleteSatellite(@PathParam("name") final String name) {
        satelliteService.deleteSatellite(name);
    }

    @DELETE
    @Path("/satellites")
    public void deleteAllSatellites() {
        satelliteService.deleteAllSatellites();
    }

    @GET
    @Path("/satelliteComs")
    public List<PlainSatelliteComDto> listSatellitesComs() {
        return PlainSatelliteComDto.fromSatellites(satelliteService.listSatellites());
    }

    @GET
    @Path("/satelliteComs/{satellite}")
    public PlainSatelliteComDto findSatelliteCom(@PathParam("satellite") final String satellite) {
        return satelliteService.findSatelliteCom(satellite)
            .map(com -> new PlainSatelliteComDto(satellite, com.receivedAt(), com.distance(), com.message()))
            .orElseThrow(() -> satelliteComNotFound(satellite))
            ;
    }

    @PUT
    @Path("/satelliteComs/{satellite}")
    public void publishSatelliteCom(
        @PathParam("satellite") final String satellite,
        final SplitSatelliteComDto satelliteCom
    ) {
        validOrFail(satelliteCom);
        satelliteService.publishSatelliteCom(satellite, satelliteCom.distance(), satelliteCom.message());
    }

    @DELETE
    @Path("/satelliteComs/{name}")
    public void deleteSatelliteCom(@PathParam("name") final String name) {
        satelliteService.deleteSatelliteCom(name);
    }

    @DELETE
    @Path("/satelliteComs")
    public void deleteAllSatellitesComs() {
        satelliteService.deleteAllSatelliteComs();
    }

    @POST
    @Path("/topsecret_split/{satellite_name}")
    public void publishSatelliteCom2(
        @PathParam("satellite_name") final String satellite_name,
        final SplitSatelliteComDto satelliteCom
    ) {
        publishSatelliteCom(satellite_name, satelliteCom);
    }
}
