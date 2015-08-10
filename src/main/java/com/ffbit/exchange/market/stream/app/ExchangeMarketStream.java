package com.ffbit.exchange.market.stream.app;

import org.glassfish.jersey.server.ResourceConfig;

public class ExchangeMarketStream extends ResourceConfig {

    public ExchangeMarketStream() {
        packages("com.ffbit.exchange.market.stream");
    }

}
