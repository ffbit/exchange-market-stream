package com.ffbit.exchange.market.stream.dao;

import com.ffbit.exchange.market.stream.common.TimeFrame;
import com.ffbit.exchange.market.stream.domain.ExchangeTransaction;
import com.ffbit.exchange.market.stream.util.TimeConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.time.OffsetDateTime;
import java.util.List;

@Repository
public class ExchangeTransactionDaoMySql implements ExchangeTransactionDao {

    @Inject
    private JdbcTemplate jdbcTemplate;

    private TimeConverter timeConverter = new TimeConverter();

    @Override
    public void save(ExchangeTransaction transaction) {
        String query = "INSERT INTO EXCHANGE_TRANSACTION" +
                " (TOOL_NAME, VOLUME, TRANSACTION_TIMESTAMP," +
                "  M1, M2, M3, M4, M5, M6, M10, M15, M30, H1, H4, D1, W1, MN)" +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        OffsetDateTime timestamp = transaction.getTimestamp();
        OffsetDateTime m1 = TimeFrame.M1.adjust(timestamp);
        OffsetDateTime m2 = TimeFrame.M2.adjust(timestamp);
        OffsetDateTime m3 = TimeFrame.M3.adjust(timestamp);
        OffsetDateTime m4 = TimeFrame.M4.adjust(timestamp);
        OffsetDateTime m5 = TimeFrame.M5.adjust(timestamp);
        OffsetDateTime m6 = TimeFrame.M6.adjust(timestamp);
        OffsetDateTime m10 = TimeFrame.M10.adjust(timestamp);
        OffsetDateTime m15 = TimeFrame.M15.adjust(timestamp);
        OffsetDateTime m30 = TimeFrame.M30.adjust(timestamp);
        OffsetDateTime h1 = TimeFrame.H1.adjust(timestamp);
        OffsetDateTime h4 = TimeFrame.H4.adjust(timestamp);
        OffsetDateTime d1 = TimeFrame.D1.adjust(timestamp);
        OffsetDateTime w1 = TimeFrame.W1.adjust(timestamp);
        OffsetDateTime mn = TimeFrame.MN.adjust(timestamp);

        jdbcTemplate.update(query,
                transaction.getToolName(),
                transaction.getVolume(),
                timeConverter.convertToDate(timestamp),
                timeConverter.convertToDate(m1),
                timeConverter.convertToDate(m2),
                timeConverter.convertToDate(m3),
                timeConverter.convertToDate(m4),
                timeConverter.convertToDate(m5),
                timeConverter.convertToDate(m6),
                timeConverter.convertToDate(m10),
                timeConverter.convertToDate(m15),
                timeConverter.convertToDate(m30),
                timeConverter.convertToDate(h1),
                timeConverter.convertToDate(h4),
                timeConverter.convertToDate(d1),
                timeConverter.convertToDate(w1),
                timeConverter.convertToDate(mn));
    }

    @Override
    public List<ExchangeTransaction> aggregateByTimeFrame(String toolName,
                                                          TimeFrame timeFrame) {
        String query = "SELECT TOOL_NAME, SUM(VOLUME) VOLUME, " + timeFrame +
                "  TRANSACTION_TIMESTAMP" +
                " FROM EXCHANGE_TRANSACTION" +
                " WHERE TOOL_NAME = ?" +
                " GROUP BY TOOL_NAME, " + timeFrame;

        try {
            List<ExchangeTransaction> transactions = jdbcTemplate.query(query,
                    new Object[]{toolName},
                    new ExchangeTransactionRowMapper()
            );

            return transactions;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
