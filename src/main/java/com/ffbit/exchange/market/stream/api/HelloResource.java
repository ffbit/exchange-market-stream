package com.ffbit.exchange.market.stream.api;

import com.ffbit.exchange.market.stream.service.HelloService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("hello")
@Component
public class HelloResource {

    @Inject
    private HelloService helloService;

    @GET
    public String hello() {
        return helloService.hello();
    }

}
