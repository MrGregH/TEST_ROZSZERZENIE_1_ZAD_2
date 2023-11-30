import org.example.ApiService;
import org.example.CurrencyExchangeRateCache;
import org.example.CurrencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CurrencyServiceTest {

    private final CurrencyExchangeRateCache cache = mock(CurrencyExchangeRateCache.class);
    private final ApiService apiService = mock(ApiService.class);
    private final CurrencyService currencyService = new CurrencyService(cache, apiService);

    @Test
    public void exchangeWithoutCallingExternalTest() {
        //GIVEN
        String currencyFrom = "USD";
        String currencyTo = "GBP";
        double amount = 10.0;
        when(cache.getOrNull(currencyFrom + currencyTo)).thenReturn(0.5);
        Double expectedResult = 10.0 * 0.5;

        //WHEN
        var result = currencyService.exchange(currencyFrom, currencyTo, amount);

        //THEN
        verify(apiService, times(0)).getRateFromExternal(anyString(), anyString());
        Assertions.assertEquals(result, expectedResult, "exchange result should be equal as expected");
    }

    @Test
    public void exchangeWithCallingExternalTest() {
        //GIVEN
        String currencyFrom = "USD";
        String currencyTo = "GBP";
        double amount = 10.0;
        double rate = 0.6;
        when(cache.getOrNull(currencyFrom + currencyTo)).thenReturn(null);
        doReturn(rate).when(apiService).getRateFromExternal(currencyFrom, currencyTo);
        Double expectedResult = amount * rate;

        //WHEN
        var result = currencyService.exchange(currencyFrom, currencyTo, amount);

        //THEN
        verify(apiService, times(1)).getRateFromExternal(anyString(), anyString());
        verify(cache, times(1)).put(currencyFrom + currencyTo, rate);
        Assertions.assertEquals(result, expectedResult, "exchange result should be equal as expected");
    }

}
