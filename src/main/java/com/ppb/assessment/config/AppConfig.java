package com.ppb.assessment.config;

import com.ppb.assessment.data.Selection;
import com.ppb.assessment.parser.BetParser;
import com.ppb.assessment.parser.CsvBetParser;
import com.ppb.assessment.report.ConsoleReportFormatter;
import com.ppb.assessment.report.ReportFormatter;
import com.ppb.assessment.report.Totals;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Currency;

@Configuration
public class AppConfig {

    private static double exchangeRate;

    @Value("${exchange.rate}")
    public void setExchangeRate(double exchangeRate) {
        AppConfig.exchangeRate = exchangeRate;
    }

    public static double getExchangeRate() {
        return exchangeRate;
    }

    @Bean
    public BetParser betParser() {
        return new CsvBetParser(",");
    }

    @Bean
    public ReportFormatter reportFormatter() {
        return new ConsoleReportFormatter<Selection, Currency, Totals>();
    }
}
