package com.dp.bvb.client.impl;

import com.dp.bvb.client.BvbApiClient;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class BvbApiClientImpl implements BvbApiClient {

    @Value("${bvb.stock-price-url}")
    private String bvbStockPriceUrl;

    private final @NonNull RestTemplate restTemplate;

    private static final String NOT_FOUND_RESPONSE = "NA";

    @Override
    public String getCurrentStockPrice(String symbol) {
        String response;
        try {
            log.info("Sending request to BVB API for stock {}", symbol);
            response = restTemplate.getForObject(bvbStockPriceUrl + symbol, String.class);
            if (response == null || NOT_FOUND_RESPONSE.equals(response)) {
                throw new IllegalArgumentException("Symbol " + symbol + " was not found!");
            }
            return response;
        } catch (RestClientException | IllegalArgumentException e) {
            log.info("Error connecting to BVB API via REST call.");
            log.debug("Error: {}", e.getLocalizedMessage());
            return null;
        }
    }
}
