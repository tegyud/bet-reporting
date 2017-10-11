package com.ppb.assessment.report;

import com.ppb.assessment.data.Bet;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Comparator.comparing;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class ReportGenerator {

    public static <G1, G2> Map<Pair<G1, G2>, Totals> groupBetsBy(List<Bet> bets, Function<Bet, G1> first,
                                                                 Function<Bet, G2> second) {
        Map<G1, Map<G2, Totals>> groupedBets = bets.stream().collect(
                groupingBy(first, groupingBy(second, Totals.collector())));

        Map<Pair<G1, G2>, Totals> totalsMap = new HashMap<>();
        for (Map.Entry<G1, Map<G2, Totals>> g1 : groupedBets.entrySet()) {
            for (Map.Entry<G2, Totals> g2 : g1.getValue().entrySet()) {
                totalsMap.put(new Pair<>(g1.getKey(), g2.getKey()), g2.getValue());
            }
        }

        return totalsMap;
    }

    public static <G1, G2, T extends Comparable<T>> Map<Pair<G1, G2>, Totals> sortTotalsBy(
            Map<Pair<G1, G2>, Totals> totals, Function<Totals, T> comparator) {
        return totals.entrySet().stream()
                .sorted(comparingByValue(comparing(comparator).reversed()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
