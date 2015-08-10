package com.ffbit.exchange.market.stream.api;

import com.ffbit.exchange.market.stream.app.ExchangeMarketStream;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PingResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        return new ExchangeMarketStream();
    }

    @Test
    public void itShouldPingSuccessfully() {
        Response response = target()
                .path("/ping")
                .request()
                .buildGet()
                .invoke();

        assertThat("Status code mismatch",
                response.getStatus(), is(Status.OK.getStatusCode()));
        assertThat("Response entity mismatch",
                response.readEntity(String.class), is("OK"));
    }

}
