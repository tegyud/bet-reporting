package com.ppb.assessment.report;

import com.ppb.assessment.config.AppConfig;
import com.ppb.assessment.data.Bet;

import java.util.Currency;
import java.util.Locale;
import java.util.stream.Collector;

public class Totals {
    private int noOfBets = 0;
    private double stakes = 0;
    private double payout = 0;
    private Currency currency;

    void accept(Bet bet) {
        noOfBets++;
        stakes += bet.getStake();
        payout += bet.getPayout();
        if (currency == null) {
            currency = bet.getCurrency();
        }
    }

    Totals combine(Totals other) {
        noOfBets += other.noOfBets;
        stakes += other.stakes;
        payout += other.payout;
        if (currency == null) {
            currency = other.currency;
        }

        return this;
    }

    static Collector<Bet, ?, Totals> collector() {
        return Collector.of(Totals::new, Totals::accept, Totals::combine);
    }

    public int getNoOfBets() {
        return noOfBets;
    }

    public double getStakes() {
        return stakes;
    }

    public double getPayout() {
        return payout;
    }

    public double getStakesInEUR() {
        return currency.equals(Currency.getInstance("GBP")) ? stakes * AppConfig.getExchangeRate() : stakes;
    }

    public double getPayoutInEUR() {
        return currency.equals(Currency.getInstance("GBP")) ? payout * AppConfig.getExchangeRate() : payout;
    }

    @Override
    public String toString() {
        return "noOfBets=" + noOfBets +
                String.format(", stakes=%s%.2f", currency.getSymbol(Locale.UK), stakes) +
                String.format(", payout=%s%.2f", currency.getSymbol(Locale.UK), payout);
    }
}
