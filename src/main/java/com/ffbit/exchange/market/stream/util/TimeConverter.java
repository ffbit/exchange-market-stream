package com.ffbit.exchange.market.stream.util;

import java.time.OffsetDateTime;
import java.util.Date;

public class TimeConverter {

    public Date convertToDate(OffsetDateTime dateTime) {
        return Date.from(dateTime.toInstant());
    }

}
