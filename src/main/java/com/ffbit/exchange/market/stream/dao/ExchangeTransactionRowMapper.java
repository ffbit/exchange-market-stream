package com.ffbit.exchange.market.stream.dao;

import com.ffbit.exchange.market.stream.domain.ExchangeTransaction;
import com.ffbit.exchange.market.stream.util.TimeConverter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ExchangeTransactionRowMapper
        implements RowMapper<ExchangeTransaction> {
    private TimeConverter timeConverter = new TimeConverter();

    @Override
    public ExchangeTransaction mapRow(ResultSet rs, int rowNum)
            throws SQLException {
        String toolName = rs.getString("TOOL_NAME");
        long volume = rs.getLong("VOLUME");
        Timestamp timestamp = rs.getTimestamp("TRANSACTION_TIMESTAMP");

        return new ExchangeTransaction(toolName)
                .withVolume(volume)
                .withTimestamp(timeConverter.convertToOffsetDateTime(timestamp));
    }

}
