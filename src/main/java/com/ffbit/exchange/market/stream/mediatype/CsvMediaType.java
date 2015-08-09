package com.ffbit.exchange.market.stream.mediatype;

import javax.ws.rs.core.MediaType;

public class CsvMediaType extends MediaType {
    private final static String TYPE = "text";
    private final static String SUBTYPE = "csv";
    public final static String TEXT_CSV = TYPE + "/" + SUBTYPE;

    public CsvMediaType() {
        super(TYPE, SUBTYPE);
    }

}
