package com.ppb.assessment.data;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.Assert.assertEquals;

public class BetTest {
    private static final double DELTA = 1e-15;

    private Bet bet;

    @Before
    public void setUp() {
        bet = new Bet("Bet-Test", 1507752105000L, new Selection(1, "Selection-1"),
                4.2, 23., Currency.getInstance("EUR"));
    }

    @Test
    public void testGetTimeStamp() throws Exception {
        // given
        final LocalDateTime expected = LocalDateTime.of(2017, 10, 11, 20, 1, 45);

        // when
        final LocalDateTime actual = bet.getTimeStamp();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void testGetPayout() throws Exception {
        // given
        final double expected = 4.2 * 23;

        // when
        final double actual = bet.getPayout();

        // then
        assertEquals(expected, actual, DELTA);
    }
}