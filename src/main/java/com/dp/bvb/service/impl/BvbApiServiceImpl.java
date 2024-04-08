package com.dp.bvb.service.impl;

import com.dp.bvb.client.BvbApiClient;
import com.dp.bvb.service.BvbApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BvbApiServiceImpl implements BvbApiService {

    private final @NonNull BvbApiClient bvbApiClient;

    @Override
    public Double getPriceForSymbol(String symbol) {
        try {
            String jsonString = bvbApiClient.getCurrentStockPrice(symbol);
            jsonString = jsonString.replace(',', '.');
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            return jsonNode.get("RON").asDouble();
        } catch (JsonProcessingException | NullPointerException e) {
            log.info("Error processing JSON received from BVB");
            throw new IllegalArgumentException("Symbol was not found!");
        }
    }
}
