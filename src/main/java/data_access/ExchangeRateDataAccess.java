package data_access;

import okhttp3.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Data access object for ExchangeRate-API.
 */
public class ExchangeRateDataAccess {

    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6";
    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your API key
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private final OkHttpClient client;

    public ExchangeRateDataAccess() {
        this.client = new OkHttpClient.Builder().build();
    }

    /**
     * Get the latest exchange rates for a base currency.
     *
     * @param baseCurrency e.g., "USD"
     * @return Map of currency codes to exchange rates
     */
    public Map<String, Double> getLatestRates(String baseCurrency) {
        String url = String.format("%s/%s/latest/%s", BASE_URL, API_KEY, baseCurrency);

        Request request = new Request.Builder()
            .url(url)
            .addHeader(CONTENT_TYPE_LABEL, CONTENT_TYPE_JSON)
            .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("Unexpected response code: " + response.code());
            }

            JSONObject jsonResponse = new JSONObject(response.body().string());

            if (!"success".equalsIgnoreCase(jsonResponse.getString("result"))) {
                throw new RuntimeException("API error: " + jsonResponse);
            }

            JSONObject rates = jsonResponse.getJSONObject("conversion_rates");
            Map<String, Double> result = new HashMap<>();
            for (String key : rates.keySet()) {
                result.put(key, rates.getDouble(key));
            }
            return result;

        } catch (IOException | JSONException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Convert an amount from one currency to another.
     *
     * @param fromCurrency e.g., "USD"
     * @param toCurrency   e.g., "EUR"
     * @param amount       e.g., 100.0
     * @return Converted amount
     */
    public double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        Map<String, Double> rates = getLatestRates(fromCurrency);
        if (!rates.containsKey(toCurrency)) {
            throw new RuntimeException("Currency not supported: " + toCurrency);
        }
        return amount * rates.get(toCurrency);
    }
}
