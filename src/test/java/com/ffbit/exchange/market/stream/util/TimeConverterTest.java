package com.ffbit.exchange.market.stream.util;

import org.junit.Test;

import java.time.OffsetDateTime;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TimeConverterTest {
    private TimeConverter converter = new TimeConverter();

    private OffsetDateTime dateTime =
            OffsetDateTime.parse("2015-01-02T12:04:05.000+07:00");

    @Test
    public void itShouldConvertFromAndToOffsetDateTimeToLegacyDate() {
        Date date = converter.convertToDate(dateTime);

        assertThat(converter.convertToOffsetDateTime(date)
                        .withOffsetSameInstant(dateTime.getOffset()),
                is(dateTime));
    }

    @Test
    public void itShouldConvertToAndFromOffsetDateTimeToLegacyDate() {
        Date date = new Date();
        OffsetDateTime dateTime = converter.convertToOffsetDateTime(date);

        assertThat(converter.convertToDate(dateTime), is(date));
    }

}
