package com.ffbit.exchange.market.stream.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

@Path("/ping")
@Component
public class PingResource {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Value("${database.validation.query}")
    private String validationQuery;

    @GET
    public Response ping() {
        ResponseBuilder responseBuilder = Response.ok("OK");

        try {
            jdbcTemplate.execute(validationQuery);
        } catch (RuntimeException e) {
            responseBuilder = Response.serverError();

            log.error("We are experiencing problems with the database server", e);
        }

        return responseBuilder.build();
    }

}
