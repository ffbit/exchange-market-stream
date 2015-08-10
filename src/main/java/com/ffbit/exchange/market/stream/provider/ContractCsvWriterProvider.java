package com.ffbit.exchange.market.stream.provider;

import com.ffbit.exchange.market.stream.domain.Contract;
import com.ffbit.exchange.market.stream.mediatype.CsvMediaType;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Provider
@Produces(CsvMediaType.TEXT_CSV)
public class ContractCsvWriterProvider extends ContractCsvProvider
        implements MessageBodyWriter<List<Contract>> {
    private final DateTimeFormatter formatter;

    public ContractCsvWriterProvider() {
        formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    }

    @Override
    public boolean isWriteable(Class<?> type,
                               Type genericType,
                               Annotation[] annotations,
                               MediaType mediaType) {
        return isAcceptable(type, genericType, annotations, mediaType);
    }

    @Override
    public long getSize(List<Contract> contracts,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType) {
        throw new UnsupportedOperationException(
                "deprecated by JAX-RS 2.0 and ignored by Jersey 2.x runtime");
    }

    @Override
    public void writeTo(List<Contract> contracts,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream)
            throws IOException, WebApplicationException {
        Writer writer = new OutputStreamWriter(entityStream);

        for (Contract contract : contracts) {
            String row = getColumns(contract).stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(COLUMN_SEPARATOR, "", LINE_SEPARATOR));
            writer.append(row);
        }

        log.debug("{} contracts are written", contracts.size());

        writer.flush();
    }

    private List<Object> getColumns(Contract contract) {
        return Arrays.asList(
                contract.getTimestamp()
                        .format(formatter),
                contract.getVolume()
        );
    }

}
