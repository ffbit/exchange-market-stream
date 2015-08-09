package com.ffbit.exchange.market.stream.provider;

import com.ffbit.exchange.market.stream.domain.ExchangeTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Provider
public class CsvReaderProvider implements
        MessageBodyReader<List<ExchangeTransaction>> {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public boolean isReadable(Class<?> type,
                              Type genericType,
                              Annotation[] annotations,
                              MediaType mediaType) {
        if (!List.class.isAssignableFrom(type)) {
            return false;
        } else if (!(genericType instanceof ParameterizedType)) {
            return false;
        }

        return getGenericArgumentType(genericType) == ExchangeTransaction.class;
    }

    private Type getGenericArgumentType(Type genericType) {
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        return parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public List<ExchangeTransaction> readFrom(Class<List<ExchangeTransaction>> type,
                                              Type genericType,
                                              Annotation[] annotations,
                                              MediaType mediaType,
                                              MultivaluedMap<String, String> httpHeaders,
                                              InputStream entityStream)
            throws IOException, WebApplicationException {
        List<ExchangeTransaction> transactions = new ArrayList<>();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(entityStream));

        String line;
        int lineNumber = 0;
        while ((line = reader.readLine()) != null) {
            lineNumber++;

            try {
                transactions.add(csvToTransaction(line));
            } catch (RuntimeException e) {
                String message = "Broken line number " + lineNumber;
                log.error(message, e);

                throw new CsvReaderProviderException(message);
            }
        }

        log.info("Converted {} lines to transactions", lineNumber);

        return transactions;
    }

    private ExchangeTransaction csvToTransaction(String line) {
        String[] parts = line.split(";");
        String toolName = parts[0];
        long volume = Long.valueOf(parts[1]);
        OffsetDateTime timestamp = OffsetDateTime.parse(parts[2]);

        return new ExchangeTransaction(toolName, volume, timestamp);
    }

}