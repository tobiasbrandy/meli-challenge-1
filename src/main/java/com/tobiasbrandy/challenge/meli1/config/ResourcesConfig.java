package com.tobiasbrandy.challenge.meli1.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.tobiasbrandy.challenge.meli1.resources.SatelliteResource;

@Component
@ApplicationPath("/")
public class ResourcesConfig extends ResourceConfig {

    public ResourcesConfig() {
        // Exception Mappers
        register(new WebExceptionMapper());
        register(new ApplicationExceptionMapper());
        register(new UnexpectedExceptionMapper());

        // Resources
        register(SatelliteResource.class);
    }
}
