package com.ffbit.exchange.market.stream.dao;

import com.ffbit.exchange.market.stream.common.TimeFrame;
import com.ffbit.exchange.market.stream.domain.ExchangeTransaction;

import java.util.List;

public interface ExchangeTransactionDao {

    void save(ExchangeTransaction transaction);

    List<ExchangeTransaction> aggregateByTimeFrame(String toolName,
                                                   TimeFrame timeFrame);

}
