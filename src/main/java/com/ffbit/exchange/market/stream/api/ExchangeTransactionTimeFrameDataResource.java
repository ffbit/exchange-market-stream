package com.ffbit.exchange.market.stream.api;

import com.ffbit.exchange.market.stream.common.TimeFrame;
import com.ffbit.exchange.market.stream.dao.ExchangeTransactionDao;
import com.ffbit.exchange.market.stream.domain.ExchangeTransaction;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Component
@Scope("prototype")
@Path("/tool/{toolName}/timeframe/{timeFrame}")
public class ExchangeTransactionTimeFrameDataResource {

    @PathParam("toolName")
    private String toolName;

    @NotNull
    @PathParam("timeFrame")
    private TimeFrame timeFrame;

    @Inject
    private ExchangeTransactionDao transactionDao;

    @GET
    @Produces("text/csv")
    public List<ExchangeTransaction> getTransactions() {
        return transactionDao.aggregateByTimeFrame(toolName, timeFrame);
    }

}
