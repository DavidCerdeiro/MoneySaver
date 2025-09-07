package com.TFG.app.backend.infraestructure.config;

import org.javamoney.moneta.convert.ecb.ECBCurrentRateProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;

@Configuration
public class CurrencyConfig {

    @Bean
    public ExchangeRateProvider ecbExchangeRateProvider() {
        return new ECBCurrentRateProvider();
    }

    @Bean
    public CurrencyConversion euroConversion(ExchangeRateProvider provider) {
        return provider.getCurrencyConversion("EUR");
    }
}
