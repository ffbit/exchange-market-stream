package com.ffbit.exchange.market.stream.api;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Component
@Scope("prototype")
@Path("/tool/{toolName}/timeframe/{timeFrame}")
public class ToolTimeFrameDataResource {

    @PathParam("toolName")
    private String toolName;

    @NotNull
    @NotEmpty
    @Length(min = 2, max = 2)
    @PathParam("timeFrame")
    private String timeFrame;

    @GET
    public String getData() {
        return "data for " + toolName + "-" + timeFrame;
    }

}
