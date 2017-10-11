package com.ppb.assessment;

import com.ppb.assessment.data.Bet;
import com.ppb.assessment.data.Selection;
import com.ppb.assessment.parser.BetParser;
import com.ppb.assessment.report.ReportFormatter;
import com.ppb.assessment.report.ReportGenerator;
import com.ppb.assessment.report.Totals;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Currency;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class BetReportingApplication implements CommandLineRunner {
	private static final Logger LOG = LogManager.getLogger();

	@Value("${data.path}")
	private String path;

	@Autowired
	private BetParser parser;
	@Autowired
	private ReportFormatter formatter;

	@Override
	public void run(String... args) throws Exception {
		final List<Bet> bets = parser.parse(path);
		LOG.info("Grouping bets");
		Map<Pair<Selection, Currency>, Totals> totalsMap =
				ReportGenerator.groupBetsBy(bets, Bet::getSelection, Bet::getCurrency);
		LOG.info("Sorting totals");
		Map<Pair<Selection, Currency>, Totals> sortedTotals =
				ReportGenerator.sortTotalsBy(totalsMap, Totals::getPayoutInEUR);
		formatter.format(sortedTotals);
	}

	public static void main(String[] args) {
		SpringApplication.run(BetReportingApplication.class, args);
	}
}
