package com.ffbit.exchange.market.stream.domain;

import java.time.OffsetDateTime;

public class Contract {
    private final String name;
    private final long volume;
    private final OffsetDateTime timestamp;

    public Contract(String name) {
        this(name, 0, OffsetDateTime.now());
    }

    public Contract(String name, long volume, OffsetDateTime timestamp) {
        this.name = name;
        this.volume = volume;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public Contract withName(String name) {
        return new Contract(name, volume, timestamp);
    }

    public long getVolume() {
        return volume;
    }

    public Contract withVolume(long volume) {
        return new Contract(name, volume, timestamp);
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public Contract withTimestamp(OffsetDateTime timestamp) {
        return new Contract(name, volume, timestamp);
    }

    @Override
    public String toString() {
        return "Contract{" +
                "name='" + name + '\'' +
                ", volume=" + volume +
                ", timestamp=" + timestamp +
                '}';
    }

}
