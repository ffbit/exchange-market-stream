package com.ffbit.exchange.market.stream.provider;

import com.ffbit.exchange.market.stream.domain.Contract;
import com.ffbit.exchange.market.stream.service.ContractCsvConverter;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

@Provider
@Component
public class ContractCsvReaderProvider extends ContractCsvProvider
        implements MessageBodyReader<List<Contract>> {

    @Inject
    private ContractCsvConverter converter;

    @Override
    public boolean isReadable(Class<?> type,
                              Type genericType,
                              Annotation[] annotations,
                              MediaType mediaType) {
        return isAcceptable(type, genericType, annotations, mediaType);
    }

    @Override
    public List<Contract> readFrom(Class<List<Contract>> type,
                                   Type genericType,
                                   Annotation[] annotations,
                                   MediaType mediaType,
                                   MultivaluedMap<String, String> httpHeaders,
                                   InputStream entityStream)
            throws IOException, WebApplicationException {
        List<Contract> contracts = converter.read(entityStream);

        log.debug("{} contracts are read", contracts.size());

        return contracts;
    }

}
