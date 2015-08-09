package com.ffbit.exchange.market.stream.util;

import org.junit.Test;

import java.time.OffsetDateTime;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TimeConverterTest {
    private TimeConverter converter = new TimeConverter();

    @Test
    public void itShouldConvertFromOffsetDateTimeToLegacyDate() {
        OffsetDateTime dateTime = OffsetDateTime.parse("2015-01-02T03:04:05.678+02:00");
        Date date = Date.from(dateTime.toInstant());

        assertThat(converter.convertToDate(dateTime), is(date));
    }

}
