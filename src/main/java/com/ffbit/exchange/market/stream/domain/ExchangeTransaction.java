package com.ffbit.exchange.market.stream.domain;

import java.time.OffsetDateTime;

public class ExchangeTransaction {
    private final String toolName;
    private final long volume;
    private final OffsetDateTime timestamp;

    public ExchangeTransaction(String toolName) {
        this(toolName, 0, OffsetDateTime.now());
    }

    public ExchangeTransaction(String toolName, long volume, OffsetDateTime timestamp) {
        this.toolName = toolName;
        this.volume = volume;
        this.timestamp = timestamp;
    }

    public String getToolName() {
        return toolName;
    }

    public ExchangeTransaction withToolName(String toolName) {
        return new ExchangeTransaction(toolName, volume, timestamp);
    }

    public long getVolume() {
        return volume;
    }

    public ExchangeTransaction withVolume(long volume) {
        return new ExchangeTransaction(toolName, volume, timestamp);
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public ExchangeTransaction withTimestamp(OffsetDateTime timestamp) {
        return new ExchangeTransaction(toolName, volume, timestamp);
    }

    @Override
    public String toString() {
        return "ExchangeTransaction{" +
                "toolName='" + toolName + '\'' +
                ", volume=" + volume +
                ", timestamp=" + timestamp +
                '}';
    }

}
