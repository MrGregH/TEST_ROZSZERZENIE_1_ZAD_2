package org.example;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || !isInteger(args[0])) {
            throw new IllegalArgumentException("provide cache duration limit argument");
        }
        CurrencyService currencyService = new CurrencyService(new CurrencyExchangeRateCache(args[0]), new ApiService());

        // Przykładowe zapytania
        double result1 = currencyService.exchange("USD", "GBP", 100.0);
        double result2 = currencyService.exchange("USD", "GBP", 100.0);
        double result3 = currencyService.exchange("USD", "EUR", 100.0);
        double result4 = currencyService.exchange("USD", "GBP", 100.0);

        System.out.println("Result 1: " + result1);
        System.out.println("Result 2: " + result2);
        System.out.println("Result 3: " + result3);
        System.out.println("Result 4: " + result4);
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

