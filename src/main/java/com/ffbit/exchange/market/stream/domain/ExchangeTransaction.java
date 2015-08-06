package com.ffbit.exchange.market.stream.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;

public class ExchangeTransaction {
    private final String toolName;
    private final BigDecimal volume;
    private final BigDecimal price1;
    private final BigDecimal price2;
    private final Instant timestamp;

    public ExchangeTransaction(String toolName) {
        this(toolName,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                Instant.now());
    }

    public ExchangeTransaction(
            String toolName,
            BigDecimal volume,
            BigDecimal price1,
            BigDecimal price2,
            Instant timestamp) {
        this.toolName = toolName;
        this.volume = volume;
        this.price1 = price1;
        this.price2 = price2;
        this.timestamp = timestamp;
    }

    public String getToolName() {
        return toolName;
    }

    public ExchangeTransaction withToolName(String toolName) {
        return new ExchangeTransaction(toolName, volume, price1, price2, timestamp);
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public ExchangeTransaction withVolume(BigDecimal volume) {
        return new ExchangeTransaction(toolName, volume, price1, price2, timestamp);
    }

    public BigDecimal getPrice1() {
        return price1;
    }

    public ExchangeTransaction withPrice1(BigDecimal price1) {
        return new ExchangeTransaction(toolName, volume, price1, price2, timestamp);
    }

    public BigDecimal getPrice2() {
        return price2;
    }

    public ExchangeTransaction withPrice2(BigDecimal price2) {
        return new ExchangeTransaction(toolName, volume, price1, price2, timestamp);
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public ExchangeTransaction withTimestamp(Instant timestamp) {
        return new ExchangeTransaction(toolName, volume, price1, price2, timestamp);
    }

    @Override
    public String toString() {
        return "ExchangeTransaction{" +
                "toolName='" + toolName + '\'' +
                ", volume=" + volume +
                ", price1=" + price1 +
                ", price2=" + price2 +
                ", timestamp=" + timestamp +
                '}';
    }

}
