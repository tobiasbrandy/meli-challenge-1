package com.tobiasbrandy.challenge.meli1.resources;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.node.TextNode;
import com.tobiasbrandy.challenge.meli1.services.MainService;

@Controller
@Produces(MediaType.APPLICATION_JSON)
@Singleton
@Path("/")
public class MainResource {

    private final MainService mainService;

    @Inject
    public MainResource(
        final MainService mainService
    ) {
        this.mainService = Objects.requireNonNull(mainService);
    }

    @GET
    @Path("/")
    public TextNode helloWorld() {
        return TextNode.valueOf("MainResource says: Hello World!");
    }
}
