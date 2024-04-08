package com.dp.bvb.client;

import com.dp.bvb.client.impl.BvbApiClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class BvbApiClientImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private BvbApiClientImpl bvbClient;

    private static final String BVB_URL = "https://api.example.com/stocks/";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(bvbClient, "bvbStockPriceUrl", BVB_URL);
    }

    @Test
    void testGetCurrentStockPrice_Success() {
        // Given
        String symbol = "AAPL";
        String response = "{\"RON\":0,5410}"; // Assuming the response is a valid stock price
        when(restTemplate.getForObject(BVB_URL + symbol, String.class)).thenReturn(response);

        // When
        String result = bvbClient.getCurrentStockPrice(symbol);

        // Then
        assertEquals(response, result);
    }

    @Test
    void testGetCurrentStockPrice_SymbolNotFound() {
        // Given
        String symbol = "INVALID";
        String response = "NA";
        when(restTemplate.getForObject(BVB_URL + symbol, String.class)).thenReturn(response);

        // When
        String result = bvbClient.getCurrentStockPrice(symbol);

        // Then
        assertEquals(null, result);
    }

    @Test
    void testGetCurrentStockPrice_RestClientException() {
        // Given
        String symbol = "AAPL";
        when(restTemplate.getForObject(BVB_URL + symbol, String.class))
                .thenThrow(new RestClientException("Mocked RestClientException"));

        // When
        String result = bvbClient.getCurrentStockPrice(symbol);

        // Then
        assertEquals(null, result);
    }

    @Test
    void testGetCurrentStockPrice_IllegalArgumentException() {
        // Given
        String symbol = "INVALID";
        when(restTemplate.getForObject(BVB_URL + symbol, String.class)).thenReturn(null);

        // When
        String result = bvbClient.getCurrentStockPrice(symbol);

        // Then
        assertEquals(null, result);
    }
}