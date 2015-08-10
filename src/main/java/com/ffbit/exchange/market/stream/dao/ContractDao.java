package com.ffbit.exchange.market.stream.dao;

import com.ffbit.exchange.market.stream.common.TimeFrame;
import com.ffbit.exchange.market.stream.domain.Contract;

import java.util.List;

public interface ContractDao {

    void save(Contract contract);

    List<Contract> aggregateByTimeFrame(String toolName,
                                        TimeFrame timeFrame);

}
