package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class CurrencyExchangeRateCache {
    private final Map<String, CacheRate> exchangeRates = new HashMap<>();
    private final long cacheDurationMillis;
    private final Object lock = new Object();


    public CurrencyExchangeRateCache(String cacheDurationMillis) {
        this.cacheDurationMillis = Long.parseLong(cacheDurationMillis);
    }

    public Double getOrNull(String name) {
        synchronized (lock) {
            var cacheRate = exchangeRates.get(name);
            if (cacheRate == null || System.currentTimeMillis() - cacheRate.time > cacheDurationMillis) {
                return null;
            }
            return cacheRate.rate;
        }
    }

    public void put(String name, Double rate) {
        exchangeRates.put(name, new CacheRate(rate));
    }

    @Getter
    @Setter
    private static class CacheRate {
        private final Double rate;
        private final long time;

        public CacheRate(Double rate) {
            this.rate = rate;
            this.time = System.currentTimeMillis();
        }
    }
}
