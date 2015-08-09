package com.ffbit.exchange.market.stream.mediatype;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CsvMediaTypeTest {
    private CsvMediaType csvMediaType = new CsvMediaType();

    @Test
    public void itShouldHaveTypeAsText() {
        assertThat(csvMediaType.getType(), is("text"));
    }

    @Test
    public void itShouldHaveSubtypeAsCsv() {
        assertThat(csvMediaType.getSubtype(), is("csv"));
    }

    @Test
    public void itShouldHaveMimeTypeAsTextSlashCsv() {
        assertThat(CsvMediaType.TEXT_CSV, is("text/csv"));
    }

}
