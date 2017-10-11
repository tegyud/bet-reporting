package com.ppb.assessment.parser;

import com.ppb.assessment.data.Bet;

import java.util.List;

public interface BetParser {
    /**
     * Getting the data from path and parsing it.
     *
     * @param path path to the source of data
     * @return List of bets
     */
    List<Bet> parse(String path);
}
