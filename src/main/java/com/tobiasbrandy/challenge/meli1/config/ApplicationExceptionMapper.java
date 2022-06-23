package com.tobiasbrandy.challenge.meli1.config;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.tobiasbrandy.challenge.meli1.models.ApplicationException;

public class ApplicationExceptionMapper implements ExceptionMapper<ApplicationException> {

    @Override
    public Response toResponse(final ApplicationException e) {
        return Response.status(Response.Status.fromStatusCode(e.getStatusCode()))
            .entity (e.toEntity())
            .type   (MediaType.APPLICATION_JSON_TYPE)
            .build  ();
    }
}
