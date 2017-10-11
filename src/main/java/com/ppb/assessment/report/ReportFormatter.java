package com.ppb.assessment.report;

import javafx.util.Pair;

import java.util.Map;

public interface ReportFormatter<G1, G2, T> {
    /**
     * Formatting and printing report to specific output.
     *
     * @param report Grouped statistics
     */
    void format(Map<Pair<G1, G2>, T> report);
}
