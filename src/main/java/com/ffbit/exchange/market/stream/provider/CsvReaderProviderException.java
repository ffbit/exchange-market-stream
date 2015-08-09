package com.ffbit.exchange.market.stream.provider;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class CsvReaderProviderException extends WebApplicationException {
    private static final long serialVersionUID = -9022946902984710479L;

    public CsvReaderProviderException(String message) {
        super(Response.serverError().entity(message).build());
    }

}
