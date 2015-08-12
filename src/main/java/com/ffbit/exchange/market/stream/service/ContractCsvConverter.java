package com.ffbit.exchange.market.stream.service;

import com.ffbit.exchange.market.stream.domain.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContractCsvConverter {
    private static final String COLUMN_SEPARATOR = ",";
    private static final String LINE_SEPARATOR = "\r\n";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final DateTimeFormatter formatter;

    public ContractCsvConverter() {
        formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
    }

    public List<Contract> read(InputStream csv) {
        List<Contract> contracts = new ArrayList<>();

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(csv));

        Optional<String> line = readLine(reader);
        int lineNumber = 0;
        while (line.isPresent()) {
            lineNumber++;

            try {
                contracts.add(csvToContract(line.get()));
            } catch (RuntimeException e) {
                String message = "Broken line number " + lineNumber;
                log.error(message, e);

                throw new ContractCsvConverterException(message, e);
            }

            line = readLine(reader);
        }

        return contracts;
    }

    private Optional<String> readLine(BufferedReader reader) {
        try {
            return Optional.ofNullable(reader.readLine());
        } catch (IOException ignored) {
            log.error("Failed to read a line", ignored);
        }

        return Optional.empty();
    }

    private Contract csvToContract(String line) {
        log.trace("csv line `{}`", line);
        String[] parts = line.split(COLUMN_SEPARATOR);
        log.trace("csv line parts `{}`", Arrays.toString(parts));
        String name = parts[0];
        long volume = Long.valueOf(parts[1]);
        OffsetDateTime timestamp = OffsetDateTime.parse(parts[2]);

        return new Contract(name, volume, timestamp);
    }

    public void write(List<Contract> contracts, OutputStream out) {
        OutputStreamWriter writer = new OutputStreamWriter(out);
        contracts.stream()
                .map(this::contractToCsv)
                .forEach(line -> write(writer, line));
        flush(writer);
    }

    private String contractToCsv(Contract contract) {
        return getColumns(contract)
                .map(String::valueOf)
                .collect(Collectors.joining(COLUMN_SEPARATOR, "", LINE_SEPARATOR));
    }

    private Stream<Object> getColumns(Contract contract) {
        return Stream.of(
                contract.getTimestamp()
                        .format(formatter),
                contract.getVolume());
    }

    private void write(OutputStreamWriter writer, String line) {
        try {
            writer.write(line);
        } catch (IOException e) {
            String message = "Could not write line " + line;
            log.error(message, e);

            throw new ContractCsvConverterException(message, e);
        }
    }

    private void flush(OutputStreamWriter writer) {
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
