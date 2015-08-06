package com.ffbit.exchange.market.stream.dao;

import com.ffbit.exchange.market.stream.domain.ExchangeTransaction;

public interface ExchangeTransactionDao {

    void save(ExchangeTransaction transaction);

}
