package com.ffbit.exchange.market.stream.api;

import com.ffbit.exchange.market.stream.mediatype.CsvMediaType;
import com.ffbit.exchange.market.stream.provider.ContractCsvReaderProvider;
import com.ffbit.exchange.market.stream.provider.ContractCsvWriterProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ContractResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        return new ResourceConfig(
                ContractResource.class,
                ContractCsvWriterProvider.class,
                ContractCsvReaderProvider.class
        );
    }

    @Test
    public void itShouldReturnCsvDataByToolAndTimeFrame() {
        Response response = get("USDCHF", "M1");

        assertThat("Status code mismatch",
                response.getStatus(), is(Status.OK.getStatusCode()));
        assertThat("Media type mismatch",
                response.getMediaType(), is(new CsvMediaType()));
        assertThat("Response mismatch",
                response.readEntity(String.class),
                is("USDCHF;2;2015-01-02T04:04:00Z\r\n"));
    }

    @Test
    public void itShouldNotReturnNotFoundForNonExistentTimeFrame() {
        Response response = get("USDCHF", "Y1");

        assertThat(response.getStatus(), is(Status.NOT_FOUND.getStatusCode()));
    }

    private Response get(String toolName, String timeFrame) {
        return target()
                .path("/tool/{toolName}/timeframe/{timeFrame}")
                .resolveTemplate("toolName", toolName)
                .resolveTemplate("timeFrame", timeFrame)
                .request()
                .buildGet()
                .invoke();
    }

    @Test
    public void itShouldCreateNewContracts() {
        String payload = "USDAUD;2;2015-01-02T03:04:05.678Z\r\n" +
                "USDAUD;3;2015-01-02T03:04:05.678Z\r\n";

        Response response = target().path("/tool")
                .request()
                .post(Entity.entity(payload, new CsvMediaType()));

        assertThat("Status code mismatch",
                response.getStatus(), is(Status.OK.getStatusCode()));
        assertThat("Response mismatch",
                response.readEntity(String.class), is("2 entries have been saved"));
    }

}
