package com.ppb.assessment.report;

import com.ppb.assessment.config.AppConfig;
import com.ppb.assessment.data.Bet;
import com.ppb.assessment.data.Selection;
import javafx.util.Pair;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class ReportGeneratorTest {

    private List<Bet> setUpBets() {
        List<Bet> bets = new ArrayList<>();
        bets.add(new Bet("Test-1", 1507752105000L, new Selection(1, "Selection-1"),
                4.2, 23., Currency.getInstance("EUR")));
        bets.add(new Bet("Test-2", 1507752106000L, new Selection(1, "Selection-1"),
                4.3, 24., Currency.getInstance("EUR")));
        bets.add(new Bet("Test-3", 1507752107000L, new Selection(1, "Selection-1"),
                4.4, 22., Currency.getInstance("GBP")));
        bets.add(new Bet("Test-4", 1507752108000L, new Selection(2, "Selection-2"),
                4.5, 21., Currency.getInstance("EUR")));

        return bets;
    }

    private Map<Pair<Selection, Currency>, Totals> setUpTotals() {
        Map<Pair<Selection, Currency>, Totals> totals = new LinkedHashMap<>();
        Totals total = new Totals();
        total.accept(new Bet("Test-1", 1507752105000L, new Selection(1, "Selection-1"),
                4.2, 23., Currency.getInstance("GBP")));
        totals.put(new Pair<>(new Selection(1, "Selection-1"), Currency.getInstance("GBP")), total);
        total = new Totals();
        total.accept(new Bet("Test-2", 1507752106000L, new Selection(1, "Selection-1"),
                4.3, 24., Currency.getInstance("EUR")));
        totals.put(new Pair<>(new Selection(1, "Selection-1"), Currency.getInstance("EUR")), total);
        total = new Totals();
        total.accept(new Bet("Test-3", 1507752107000L, new Selection(2, "Selection-2"),
                4.4, 22., Currency.getInstance("EUR")));
        totals.put(new Pair<>(new Selection(2, "Selection-2"), Currency.getInstance("EUR")), total);

        return totals;
    }

    private void setUpExchangeRate() {
        AppConfig appConfig = new AppConfig();
        appConfig.setExchangeRate(1.14);
    }

    @Test
    public void testGroupBetsBySelectionThenCurrency() {
        // given
        final String expected = "{Selection-1=EUR=noOfBets=2, stakes=€8.50, payout=€199.80, " +
                "Selection-1=GBP=noOfBets=1, stakes=£4.40, payout=£96.80, " +
                "Selection-2=EUR=noOfBets=1, stakes=€4.50, payout=€94.50}";
        List<Bet> bets = setUpBets();

        // when
        final Map<Pair<Selection, Currency>, Totals> totals = ReportGenerator.groupBetsBy(bets, Bet::getSelection, Bet::getCurrency);
        final Map<Pair<Selection, Currency>, Totals> actual = ReportGenerator.sortTotalsBy(totals, Totals::getPayout);

        // then
        assertEquals(expected, actual.toString());
    }

    @Test
    public void testGroupBetsByCurrencyThenSelection() {
        // given
        final String expected = "{EUR=Selection-1=noOfBets=2, stakes=€8.50, payout=€199.80, " +
                "GBP=Selection-1=noOfBets=1, stakes=£4.40, payout=£96.80, " +
                "EUR=Selection-2=noOfBets=1, stakes=€4.50, payout=€94.50}";
        List<Bet> bets = setUpBets();

        // when
        final Map<Pair<Currency, Selection>, Totals> totals = ReportGenerator.groupBetsBy(bets, Bet::getCurrency, Bet::getSelection);
        final Map<Pair<Currency, Selection>, Totals> actual = ReportGenerator.sortTotalsBy(totals, Totals::getPayout);

        // then
        assertEquals(expected, actual.toString());
    }

    @Test
    public void testSortTotalsByPayout() {
        // given
        String expected = "{Selection-1=EUR=noOfBets=1, stakes=€4.30, payout=€103.20, " +
                "Selection-2=EUR=noOfBets=1, stakes=€4.40, payout=€96.80, " +
                "Selection-1=GBP=noOfBets=1, stakes=£4.20, payout=£96.60}";
        Map<Pair<Selection, Currency>, Totals> totals = setUpTotals();

        // when
        final Map<Pair<Selection, Currency>, Totals> actual = ReportGenerator.sortTotalsBy(totals, Totals::getPayout);

        // then
        assertEquals(expected, actual.toString());
    }

    @Test
    public void testSortTotalsByPayoutInEUR() {
        // given
        String expected = "{Selection-1=GBP=noOfBets=1, stakes=£4.20, payout=£96.60, " +
                "Selection-1=EUR=noOfBets=1, stakes=€4.30, payout=€103.20, " +
                "Selection-2=EUR=noOfBets=1, stakes=€4.40, payout=€96.80}";
        Map<Pair<Selection, Currency>, Totals> totals = setUpTotals();
        setUpExchangeRate();

        // when
        final Map<Pair<Selection, Currency>, Totals> actual = ReportGenerator.sortTotalsBy(totals, Totals::getPayoutInEUR);

        // then
        assertEquals(expected, actual.toString());
    }

    @Test
    public void testSortTotalsByStakes() {
        // given
        String expected = "{Selection-2=EUR=noOfBets=1, stakes=€4.40, payout=€96.80, " +
                "Selection-1=EUR=noOfBets=1, stakes=€4.30, payout=€103.20, " +
                "Selection-1=GBP=noOfBets=1, stakes=£4.20, payout=£96.60}";
        Map<Pair<Selection, Currency>, Totals> totals = setUpTotals();
        setUpExchangeRate();

        // when
        final Map<Pair<Selection, Currency>, Totals> actual = ReportGenerator.sortTotalsBy(totals, Totals::getStakes);

        // then
        assertEquals(expected, actual.toString());
    }
}