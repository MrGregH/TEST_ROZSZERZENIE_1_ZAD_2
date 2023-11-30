import org.example.ApiService;
import org.example.CurrencyExchangeRateCache;
import org.example.CurrencyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.mockito.Mockito.*;

public class CurrencyExchangeRateCacheTest {

    private final String cacheDurableLimit = "100";
    private final CurrencyExchangeRateCache currencyService = new CurrencyExchangeRateCache(cacheDurableLimit);

    @Test
    public void cacheShouldReturnTheValue() {
        //GIVEN
        String name = "USDGBP";
        Double expectedResult = 10.0;
        currencyService.put(name, expectedResult);

        //WHEN
        var result = currencyService.getOrNull(name);

        //THEN
        Assertions.assertEquals(result, expectedResult, "Rate should be retrieved from the cache");
    }

    @Test
    public void cacheShouldBeExpired() throws InterruptedException {
        //GIVEN
        String name = "USDGBP";
        currencyService.put(name, 10.0);
        sleep(Long.parseLong(cacheDurableLimit) + 100);

        //WHEN
        var result = currencyService.getOrNull(name);

        //THEN
        Assertions.assertNull(result, "Rate should not be retrieved from the cache");
    }
}
