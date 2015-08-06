package com.ffbit.exchange.market.stream.dao;

import com.ffbit.exchange.market.stream.domain.ExchangeTransaction;
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
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;

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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void itShouldSaveExchangeTransaction() {
        String toolName = "USDCHF";
        BigDecimal volume = new BigDecimal(1);
        BigDecimal price1 = new BigDecimal(2);
        BigDecimal price2 = new BigDecimal(3);
        Instant timestamp = Instant.now();

        ExchangeTransaction transaction = new ExchangeTransaction(toolName)
                .withVolume(volume)
                .withPrice1(price1)
                .withPrice2(price2)
                .withTimestamp(timestamp);

        dao.save(transaction);

        verify(jdbcTemplate).update(anyString(), eq(toolName),
                eq(volume), eq(price1), eq(price2), eq(Date.from(timestamp)));
    }

}
