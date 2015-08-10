package com.ffbit.exchange.market.stream.provider;


import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ContractCsvReaderProviderException extends WebApplicationException {
    private static final long serialVersionUID = -9022946902984710479L;

    public ContractCsvReaderProviderException(String message, Throwable cause) {
        super(message, cause, Response.serverError().entity(message).build());
    }

}
