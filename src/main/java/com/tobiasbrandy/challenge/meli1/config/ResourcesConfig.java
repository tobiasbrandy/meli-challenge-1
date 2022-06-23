package com.tobiasbrandy.challenge.meli1.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/")
public class ResourcesConfig extends ResourceConfig {

    public ResourcesConfig() {
        register(com.tobiasbrandy.challenge.meli1.resources.MainResource.class);
    }
}
