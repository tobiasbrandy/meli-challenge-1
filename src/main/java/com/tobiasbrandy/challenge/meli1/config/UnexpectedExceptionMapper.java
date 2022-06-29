package com.tobiasbrandy.challenge.meli1.config;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tobiasbrandy.challenge.meli1.validation.BaseErrorEntity;

public class UnexpectedExceptionMapper implements ExceptionMapper<Exception> {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnexpectedExceptionMapper.class);

    @Override
    public Response toResponse(final Exception e) {
        LOGGER.error("Unexpected error", e);
        return Response.status(Response.Status.fromStatusCode(500))
            .entity (new BaseErrorEntity(0, "Unexpected exception: " + e.getMessage()))
            .type   (MediaType.APPLICATION_JSON_TYPE)
            .build  ();
    }
}
