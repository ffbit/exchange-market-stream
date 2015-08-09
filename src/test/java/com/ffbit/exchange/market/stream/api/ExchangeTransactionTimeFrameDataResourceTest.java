package com.ffbit.exchange.market.stream.api;

import com.ffbit.exchange.market.stream.provider.CsvWriterProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ExchangeTransactionTimeFrameDataResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        return new ResourceConfig(
                ExchangeTransactionTimeFrameDataResource.class,
                CsvWriterProvider.class
        );
    }

    @Test
    public void itShouldReturnCsvDataByToolAndTimeFrame() {
        Response response = invoke("USDCHF", "M1");

        assertThat("Status code mismatch",
                response.getStatus(), is(Status.OK.getStatusCode()));
        assertThat("Media type mismatch",
                response.getMediaType(), is(new MediaType("text", "csv")));
        assertThat(
                response.readEntity(String.class),
                is("USDCHF;1.2;3.4;5.6;2015-01-02T03:04:05.678Z\r\n"));
    }

    @Test
    public void itShouldNotReturnNotFoundForNonExistentTimeFrame() {
        Response response = invoke("USDCHF", "Y1");

        assertThat(response.getStatus(), is(Status.NOT_FOUND.getStatusCode()));
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
