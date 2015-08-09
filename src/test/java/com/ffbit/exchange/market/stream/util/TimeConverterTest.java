package com.ffbit.exchange.market.stream.util;

import org.junit.Test;

import java.time.OffsetDateTime;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TimeConverterTest {
    private TimeConverter converter = new TimeConverter();

    private OffsetDateTime dateTime =
            OffsetDateTime.parse("2015-01-02T03:04:05.678Z");

    @Test
    public void itShouldConvertFromAndToOffsetDateTimeToLegacyDate() {
        OffsetDateTime dateTime =
                OffsetDateTime.parse("2015-01-02T03:04:05.678Z");
        Date date = converter.convertToDate(dateTime);

        assertThat(converter.convertToOffsetDateTime(date), is(dateTime));
    }

    @Test
    public void itShouldConvertToAndFromOffsetDateTimeToLegacyDate() {
        Date date = new Date();
        OffsetDateTime dateTime = converter.convertToOffsetDateTime(date);

        assertThat(converter.convertToDate(dateTime), is(date));
    }

}
