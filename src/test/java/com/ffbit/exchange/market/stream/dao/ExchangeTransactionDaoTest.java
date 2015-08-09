package com.ffbit.exchange.market.stream.dao;

import com.ffbit.exchange.market.stream.common.TimeFrame;
import com.ffbit.exchange.market.stream.domain.ExchangeTransaction;
import com.ffbit.exchange.market.stream.util.TimeConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.time.OffsetDateTime;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ExchangeTransactionDaoTest {

    @Spy
    @Inject
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    @Inject
    private ExchangeTransactionDao dao;

    private TimeConverter timeConverter = new TimeConverter();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldSaveExchangeTransaction() {
        String toolName = "USDCHF";
        long volume = 1;
        OffsetDateTime timestamp = OffsetDateTime.parse("2015-01-02T03:04:05.678+02:00");

        ExchangeTransaction transaction = new ExchangeTransaction(toolName)
                .withVolume(volume)
                .withTimestamp(timestamp);

        dao.save(transaction);

        verify(jdbcTemplate).update(anyString(), eq(toolName), eq(volume),
                eq(timeConverter.convertToDate(timestamp)),
                eq(timeConverter.convertToDate(TimeFrame.M1.adjust(timestamp))),
                eq(timeConverter.convertToDate(TimeFrame.M2.adjust(timestamp))),
                eq(timeConverter.convertToDate(TimeFrame.M3.adjust(timestamp))),
                eq(timeConverter.convertToDate(TimeFrame.M4.adjust(timestamp))),
                eq(timeConverter.convertToDate(TimeFrame.M5.adjust(timestamp))),
                eq(timeConverter.convertToDate(TimeFrame.M6.adjust(timestamp))),
                eq(timeConverter.convertToDate(TimeFrame.M10.adjust(timestamp))),
                eq(timeConverter.convertToDate(TimeFrame.M15.adjust(timestamp))),
                eq(timeConverter.convertToDate(TimeFrame.M30.adjust(timestamp))),
                eq(timeConverter.convertToDate(TimeFrame.H1.adjust(timestamp))),
                eq(timeConverter.convertToDate(TimeFrame.H4.adjust(timestamp))),
                eq(timeConverter.convertToDate(TimeFrame.D1.adjust(timestamp))),
                eq(timeConverter.convertToDate(TimeFrame.W1.adjust(timestamp))),
                eq(timeConverter.convertToDate(TimeFrame.MN.adjust(timestamp)))
        );
    }

}
