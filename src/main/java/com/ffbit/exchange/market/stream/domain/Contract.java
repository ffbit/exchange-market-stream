package com.ffbit.exchange.market.stream.domain;

import java.time.OffsetDateTime;

public class Contract {
    private final String toolName;
    private final long volume;
    private final OffsetDateTime timestamp;

    public Contract(String toolName) {
        this(toolName, 0, OffsetDateTime.now());
    }

    public Contract(String toolName, long volume, OffsetDateTime timestamp) {
        this.toolName = toolName;
        this.volume = volume;
        this.timestamp = timestamp;
    }

    public String getToolName() {
        return toolName;
    }

    public Contract withToolName(String toolName) {
        return new Contract(toolName, volume, timestamp);
    }

    public long getVolume() {
        return volume;
    }

    public Contract withVolume(long volume) {
        return new Contract(toolName, volume, timestamp);
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public Contract withTimestamp(OffsetDateTime timestamp) {
        return new Contract(toolName, volume, timestamp);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "toolName='" + toolName + '\'' +
                ", volume=" + volume +
                ", timestamp=" + timestamp +
                '}';
    }

}
