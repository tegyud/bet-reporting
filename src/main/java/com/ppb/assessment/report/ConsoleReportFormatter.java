package com.ppb.assessment.report;

import javafx.util.Pair;

import java.util.Map;

public class ConsoleReportFormatter<G1, G2, T> implements ReportFormatter<G1, G2, T> {

    @Override
    public void format(Map<Pair<G1, G2>, T> report) {
        System.out.println("-=Bet Report=-");
        report.forEach((k, v) -> {
            System.out.print(k.getKey().toString() + " in " + k.getValue() + ": ");
            System.out.println(v);
        });
    }
}
