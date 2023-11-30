package org.example;

public class CurrencyService {

    private final CurrencyExchangeRateCache exchangeRateCache;
    private final ApiService apiService;

    public CurrencyService(CurrencyExchangeRateCache cache, ApiService apiService) {
        this.exchangeRateCache = cache;
        this.apiService = apiService;
    }

    public double exchange(String fromCurrency, String toCurrency, double amount) {
        double exchangeRate = getExchangeRate(fromCurrency, toCurrency);
        return amount * exchangeRate;
    }

    public double getExchangeRate(String fromCurrency, String toCurrency) {
        var name = fromCurrency + toCurrency;
        var rate = exchangeRateCache.getOrNull(name);
        if (rate == null) {
            rate = apiService.getRateFromExternal(fromCurrency, toCurrency);
            exchangeRateCache.put(name, rate);
        }
        return rate;
    }
}
