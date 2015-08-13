package com.ffbit.exchange.market.stream.api;

import com.ffbit.exchange.market.stream.app.ExchangeMarketStream;
import com.ffbit.exchange.market.stream.mediatype.CsvMediaType;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;

import static javax.ws.rs.core.Response.Status;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ContractResourceTest extends JerseyTest {
    private String inputCsv = "USDAUD,2,2015-01-02T03:04:05.678Z\r\n" +
            "USDAUD,3,2015-01-02T03:04:05.678Z\r\n";

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        return new ExchangeMarketStream();
    }

    @Override
    protected void configureClient(ClientConfig config) {
        config.register(MultiPartFeature.class);
    }

    @Test
    public void itShouldReturnCsvDataByNameAndTimeFrame() {
        Response response = get("USDCHF", "M1");

        assertThat("Status code mismatch",
                response.getStatus(), is(Status.OK.getStatusCode()));
        assertThat("Media type mismatch",
                response.getMediaType(), is(new CsvMediaType()));
        assertThat("Response mismatch",
                response.readEntity(String.class),
                is("2015.01.02 02:04:00,2\r\n"));
    }

    @Test
    public void itShouldNotReturnNotFoundForNonExistentTimeFrame() {
        Response response = get("USDCHF", "Y1");

        assertThat(response.getStatus(), is(Status.NOT_FOUND.getStatusCode()));
    }

    private Response get(String name, String timeFrame) {
        return target()
                .path("/contract/{name}/timeframe/{timeFrame}")
                .resolveTemplate("name", name)
                .resolveTemplate("timeFrame", timeFrame)
                .request()
                .buildGet()
                .invoke();
    }

    @Test
    public void itShouldCreateNewContracts() {
        Response response = target().path("/contract")
                .request()
                .post(Entity.entity(inputCsv, new CsvMediaType()));

        assertThat("Status code mismatch",
                response.getStatus(), is(Status.OK.getStatusCode()));
        assertThat("Response mismatch",
                response.readEntity(String.class), is("2 entries have been saved"));
    }

    @Test
    public void itShouldCreateNewContractsFromFileUploaded() {
        ByteArrayInputStream stream =
                new ByteArrayInputStream(inputCsv.getBytes());
        StreamDataBodyPart file = new StreamDataBodyPart("file", stream);
        MultiPart multipart = new FormDataMultiPart()
                .bodyPart(file);

        Response response = target().path("/contract")
                .request()
                .post(Entity.entity(multipart, multipart.getMediaType()));

        assertThat("Status code mismatch",
                response.getStatus(), is(Status.OK.getStatusCode()));
        assertThat("Response mismatch",
                response.readEntity(String.class), is("2 entries have been saved"));
    }

}
