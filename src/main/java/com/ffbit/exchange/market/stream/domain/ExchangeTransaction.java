package com.ffbit.exchange.market.stream.domain;

import java.time.Instant;

public class ExchangeTransaction {
    private final String toolName;
    private final long volume;
    private final Instant timestamp;

    public ExchangeTransaction(String toolName) {
        this(toolName, 0, Instant.now());
    }

    public ExchangeTransaction(String toolName, long volume, Instant timestamp) {
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

    public Instant getTimestamp() {
        return timestamp;
    }

    public ExchangeTransaction withTimestamp(Instant timestamp) {
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
