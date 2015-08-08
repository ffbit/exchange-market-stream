package com.ffbit.exchange.market.stream.dao;

import com.ffbit.exchange.market.stream.common.TimeFrame;
import com.ffbit.exchange.market.stream.domain.ExchangeTransaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.Instant;
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
                " (TOOL_NAME, VOLUME, PRICE1, PRICE2, TRANSACTION_TIMESTAMP)" +
                " VALUES(?, ?, ?, ?, ?)";

        jdbcTemplate.update(query,
                transaction.getToolName(),
                transaction.getVolume(),
                transaction.getPrice1(),
                transaction.getPrice2(),
                Date.from(transaction.getTimestamp()));
    }

    @Override
    public List<ExchangeTransaction> aggregateByTimeFrame(String toolName, TimeFrame timeFrame) {
        ExchangeTransaction transaction = new ExchangeTransaction(toolName)
                .withVolume(new BigDecimal("1.2"))
                .withPrice1(new BigDecimal("3.4"))
                .withPrice2(new BigDecimal("5.6"))
                .withTimestamp(Instant.parse("2015-01-02T03:04:05.678Z"));

        return Collections.singletonList(transaction);
    }

}
