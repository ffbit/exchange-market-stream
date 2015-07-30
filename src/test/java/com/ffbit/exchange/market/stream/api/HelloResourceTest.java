package com.ffbit.exchange.market.stream.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HelloResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        return new ResourceConfig(HelloResource.class);
    }

    @Test
    public void itShouldSayHello() {
        String response = target()
                .path("hello")
                .request()
                .get(String.class);

        assertThat(response, is("Hello!"));
    }

}
