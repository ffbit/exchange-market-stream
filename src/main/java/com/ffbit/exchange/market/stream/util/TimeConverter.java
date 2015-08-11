package com.ffbit.exchange.market.stream.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

public class TimeConverter {

    public Date convertToDate(OffsetDateTime dateTime) {
        LocalDateTime localDateTime = dateTime.withOffsetSameInstant(ZoneOffset.UTC)
                .toLocalDateTime();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());

        return Date.from(zonedDateTime.toInstant());
    }

    public OffsetDateTime convertToOffsetDateTime(Date dateTime) {
        Instant instant = Instant.ofEpochMilli(dateTime.getTime());
        LocalDateTime localDatetime =
                LocalDateTime.ofInstant(instant, ZoneOffset.systemDefault());

        return OffsetDateTime.of(localDatetime, ZoneOffset.UTC);
    }

}
