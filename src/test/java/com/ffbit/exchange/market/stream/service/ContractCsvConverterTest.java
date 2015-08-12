package com.ffbit.exchange.market.stream.service;

import com.ffbit.exchange.market.stream.domain.Contract;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.OffsetDateTime;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@Service
public class ContractCsvConverterTest {
    private String inputCsv = "USDAUD,3,2015-01-02T03:04:05.678+01:00\r\n" +
            "USDCHF,2,2015-02-03T04:05:06.789+02:00\r\n";

    private Contract usdAud = new Contract("USDAUD")
            .withVolume(3)
            .withTimestamp(OffsetDateTime.parse("2015-01-02T03:04:05.678+01:00"));
    private Contract usdChf = new Contract("USDCHF")
            .withVolume(2)
            .withTimestamp(OffsetDateTime.parse("2015-02-03T04:05:06.789+02:00"));

    private String outputCsv = "2015.01.02 03:04:05,3\r\n" +
            "2015.02.03 04:05:06,2\r\n";


    private ContractCsvConverter converter = new ContractCsvConverter();

    @Test
    public void itShouldReadCsvToContracts() {
        InputStream csv = new ByteArrayInputStream(inputCsv.getBytes());

        assertThat(converter.read(csv), is(asList(usdAud, usdChf)));
    }

    @Test
    public void itShouldWriteContractsCsv() {
        OutputStream out = new ByteArrayOutputStream();

        converter.write(asList(usdAud, usdChf), out);

        assertThat(out.toString(), is(outputCsv));
    }

}
