package com.ppb.assessment.parser;

import com.ppb.assessment.data.Bet;
import com.ppb.assessment.data.Selection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class CsvBetParser implements BetParser {
    private static final Logger LOG = LogManager.getLogger();
    private static final short NUMBER_OF_FIELDS = 7;

    private final String separator;

    public CsvBetParser(String separator) {
        this.separator = separator;
    }

    @Override
    public List<Bet> parse(String path) {
        LOG.info("Parsing bets from " + path);
        List<Bet> bets = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
            if (!isHeaderValid(reader.readLine())) {
                throw new IllegalArgumentException();
            }
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] fields = line.split(separator, NUMBER_OF_FIELDS);
                Bet bet = new Bet(
                        fields[0],
                        Long.parseLong(fields[1]),
                        new Selection(Long.parseLong(fields[2]), fields[3]),
                        Double.parseDouble(fields[4]),
                        Double.parseDouble(fields[5]),
                        Currency.getInstance(fields[6]));
                bets.add(bet);
            }
        } catch (IOException e) {
            LOG.error("Cannot parse csv", e);
        }
        return bets;
    }

    private boolean isHeaderValid(String header) {
        return StringUtils.countOccurrencesOf(header, separator) == NUMBER_OF_FIELDS - 1;
    }
}
