package com.ffbit.exchange.market.stream.provider;

import com.ffbit.exchange.market.stream.domain.Contract;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Provider
public class ContractCsvReaderProvider extends ContractCsvProvider
        implements MessageBodyReader<List<Contract>> {

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
        List<Contract> contracts = new ArrayList<>();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(entityStream));

        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            lineNumber++;

            try {
                contracts.add(csvToContract(line));
            } catch (RuntimeException e) {
                String message = "Broken line number " + lineNumber;
                log.error(message, e);

                throw new ContractCsvReaderProviderException(message, e);
            }
        }

        log.info("Converted {} lines to contracts", lineNumber);

        return contracts;
    }

    private Contract csvToContract(String line) {
        log.debug("line {}", line);
        String[] parts = line.split(COLUMN_SEPARATOR);
        log.debug("parts {}", Arrays.toString(parts));
        String name = parts[0];
        long volume = Long.valueOf(parts[1]);
        OffsetDateTime timestamp = OffsetDateTime.parse(parts[2]);

        return new Contract(name, volume, timestamp);
    }

}
