package com.ffbit.exchange.market.stream.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ToolTimeFrameDataResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        return new ResourceConfig(ToolTimeFrameDataResource.class);
    }

    @Test
    public void itShouldReturnDataByToolAndTimeFrame() {
        Response response = invoke("USDCHF", "M1");

        assertThat("Status code mismatch",
                response.getStatus(), is(Status.OK.getStatusCode()));
        assertThat(response.readEntity(String.class), is("data for USDCHF-M1"));
    }

    @Test
    public void itShouldNotReturnBadRequestIfTimeFrameIsLessThanTwoSymbols() {
        Response response = invoke("USDCHF", "a");

        assertThat(response.getStatus(), is(Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void itShouldNotReturnBadRequestIfTimeFrameIsMoreThanTwoSymbols() {
        Response response = invoke("USDCHF", "abc");

        assertThat(response.getStatus(), is(Status.BAD_REQUEST.getStatusCode()));
    }

    private Response invoke(String toolName, String timeFrame) {
        return target()
                .path("/tool/{toolName}/timeframe/{timeFrame}")
                .resolveTemplate("toolName", toolName)
                .resolveTemplate("timeFrame", timeFrame)
                .request()
                .buildGet()
                .invoke();
    }

}
