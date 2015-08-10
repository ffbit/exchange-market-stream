package com.ffbit.exchange.market.stream.provider;

import com.ffbit.exchange.market.stream.domain.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class ContractCsvProvider {
    protected static final String COLUMN_SEPARATOR = ";";
    protected static final String LINE_SEPARATOR = "\r\n";
    protected Logger log = LoggerFactory.getLogger(getClass());

    public boolean isAcceptable(Class<?> type,
                                Type genericType,
                                Annotation[] annotations,
                                MediaType mediaType) {
        if (!List.class.isAssignableFrom(type)) {
            return false;
        } else if (!(genericType instanceof ParameterizedType)) {
            return false;
        }

        return getGenericArgumentType(genericType) == Contract.class;
    }

    private Type getGenericArgumentType(Type genericType) {
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        return parameterizedType.getActualTypeArguments()[0];
    }

}
