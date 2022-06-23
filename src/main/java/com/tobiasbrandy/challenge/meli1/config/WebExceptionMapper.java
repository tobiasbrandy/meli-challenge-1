package com.tobiasbrandy.challenge.meli1.config;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.tobiasbrandy.challenge.meli1.models.ApplicationException;

public class WebExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(final WebApplicationException e) {
        return Response.status(e.getResponse().getStatusInfo().toEnum())
            .entity (new ApplicationException.ErrorEntity(1, e.getMessage()))
            .type   (MediaType.APPLICATION_JSON_TYPE)
            .build  ();
    }

}
