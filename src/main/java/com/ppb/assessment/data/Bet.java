package com.ppb.assessment.data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Currency;

public class Bet {
    private String id;
    private LocalDateTime timeStamp;
    private Selection selection;
    private double stake;
    private double price;
    private Currency currency;

    public Bet(String id, long timeStamp, Selection selection, double stake, double price, Currency currency) {
        this.id = id;
        this.timeStamp = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneOffset.UTC);
        this.selection = selection;
        this.stake = stake;
        this.price = price;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public Selection getSelection() {
        return selection;
    }

    public double getStake() {
        return stake;
    }

    public double getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getPayout() {
        return stake * price;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "id='" + id + '\'' +
                ", timeStamp=" + timeStamp +
                ", selection=" + selection +
                ", stake=" + stake +
                ", price=" + price +
                ", currency=" + currency +
                '}';
    }
}
