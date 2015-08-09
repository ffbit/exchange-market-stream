package com.ffbit.exchange.market.stream.dao;

import com.ffbit.exchange.market.stream.common.TimeFrame;
import com.ffbit.exchange.market.stream.domain.ExchangeTransaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Repository
public class ExchangeTransactionDaoMySql implements ExchangeTransactionDao {

    @Inject
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(ExchangeTransaction transaction) {
        String query = "INSERT INTO EXCHANGE_TRANSACTION" +
                " (TOOL_NAME, VOLUME, TRANSACTION_TIMESTAMP)" +
                " VALUES(?, ?, ?)";

        jdbcTemplate.update(query,
                transaction.getToolName(),
                transaction.getVolume(),
                Date.from(transaction.getTimestamp().toInstant()));
    }

    @Override
    public List<ExchangeTransaction> aggregateByTimeFrame(String toolName, TimeFrame timeFrame) {
        ExchangeTransaction transaction = new ExchangeTransaction(toolName)
                .withVolume(1)
                .withTimestamp(OffsetDateTime.parse("2015-01-02T03:04:05.678Z"));

        return Collections.singletonList(transaction);
    }

}
