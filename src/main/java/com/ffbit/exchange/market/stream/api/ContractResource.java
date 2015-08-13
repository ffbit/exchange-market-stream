package com.ffbit.exchange.market.stream.api;

import com.ffbit.exchange.market.stream.common.TimeFrame;
import com.ffbit.exchange.market.stream.dao.ContractDao;
import com.ffbit.exchange.market.stream.domain.Contract;
import com.ffbit.exchange.market.stream.mediatype.CsvMediaType;
import com.ffbit.exchange.market.stream.service.ContractCsvConverter;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Scope("prototype")
@Path("/contract")
public class ContractResource {

    @Inject
    private ContractDao contractDao;

    @Inject
    private ContractCsvConverter contractConverter;

    @GET
    @Produces(CsvMediaType.TEXT_CSV)
    @Path("/{name}/timeframe/{timeFrame}")
    public List<Contract> getContracts(
            @PathParam("name") String name,
            @PathParam("timeFrame") TimeFrame timeFrame) {
        return contractDao.aggregateByTimeFrame(name, timeFrame);
    }

    @POST
    public String createContracts(List<Contract> contracts) {
        contracts.forEach(contractDao::save);

        return contracts.size() + " entries have been saved";
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public String createContractsFromFile(
            @FormDataParam("file") InputStream file
    ) throws IOException, WebApplicationException {
        List<Contract> contracts = contractConverter.read(file);
        return createContracts(contracts);
    }

}
