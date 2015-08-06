package com.ffbit.exchange.market.stream.dao;

import com.ffbit.exchange.market.stream.domain.ExchangeTransaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Date;

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

}
