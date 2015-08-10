package com.ffbit.exchange.market.stream.api;

import com.ffbit.exchange.market.stream.common.TimeFrame;
import com.ffbit.exchange.market.stream.dao.ContractDao;
import com.ffbit.exchange.market.stream.domain.Contract;
import com.ffbit.exchange.market.stream.mediatype.CsvMediaType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Component
@Scope("prototype")
@Path("/tool")
public class ContractResource {

    @Inject
    private ContractDao transactionDao;

    @GET
    @Produces(CsvMediaType.TEXT_CSV)
    @Path("/{toolName}/timeframe/{timeFrame}")
    public List<Contract> getTransactions(
            @PathParam("toolName") String toolName,
            @PathParam("timeFrame") TimeFrame timeFrame) {
        return transactionDao.aggregateByTimeFrame(toolName, timeFrame);
    }

    @POST
    public String createTransactions(List<Contract> transactions) {
        for (Contract transaction : transactions) {
            transactionDao.save(transaction);
        }

        return transactions.size() + " entries have been saved";
    }

}
