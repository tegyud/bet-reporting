package com.ppb.assessment.parser;

import com.ppb.assessment.data.Bet;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CsvBetParserTest {

    private BetParser parser;

    @Before
    public void setUp() {
        parser = new CsvBetParser(",");
    }

    @Test(expected = IllegalArgumentException.class)
    public void parseFailsWhenHeaderIsNotValid() throws Exception {
        // when
        parser.parse("src/test/resources/invalid_header.csv");
    }

    @Test
    public void parseReturnsEmptyListWhenFileNotFound() {
        // when
        final List<Bet> bets = parser.parse("404.csv");

        // then
        assertTrue(bets.isEmpty());
    }

    @Test
    public void parseReturnsWithExpectedAmountOfBets() {
        // when
        final List<Bet> bets = parser.parse("src/test/resources/test_bets.csv");

        // then
        assertEquals(3, bets.size());
    }
}