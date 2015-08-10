package com.ffbit.exchange.market.stream.app;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

public class ExchangeMarketStream extends ResourceConfig {

    public ExchangeMarketStream() {
        packages("com.ffbit.exchange.market.stream");
        register(MultiPartFeature.class);
        register(LoggingFilter.class);
    }

}
